package guru.qa.niffler.jupiter.extension;

import io.qameta.allure.Allure;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class UsersQueueExtension implements
        BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UsersQueueExtension.class);

    public record StaticUser (
            String username,
            String password,
            String friend,
            String income_requests,
            String outcome_requests) {
    }

    private final static Queue<StaticUser> EMPTY_USERS = new ConcurrentLinkedQueue<>();
    private final static Queue<StaticUser> USERS_WITH_FRIENDS = new ConcurrentLinkedQueue<>();
    private final static Queue<StaticUser> USERS_WITH_INCOME_REQUESTS = new ConcurrentLinkedQueue<>();
    private final static Queue<StaticUser> USERS_WITH_OUTCOME_REQUESTS = new ConcurrentLinkedQueue<>();

    static {
        EMPTY_USERS.add(new StaticUser("vasya", "123456", null, null, null));
        USERS_WITH_FRIENDS.add(new StaticUser("petr", "123456", "friend", null, null));
        USERS_WITH_INCOME_REQUESTS.add(new StaticUser("userIncome", "123456", null, "alex", null));
        USERS_WITH_OUTCOME_REQUESTS.add(new StaticUser("userOutcome", "123456", null, null, "admin"));
    }

    private Queue<StaticUser> getQueueByType(UserType.Type userType) {
       return switch (userType) {
           case EMPTY -> EMPTY_USERS;
           case WITH_FRIENDS -> USERS_WITH_FRIENDS;
           case WITH_INCOME_REQUEST -> USERS_WITH_INCOME_REQUESTS;
           case WITH_OUTCOME_REQUEST -> USERS_WITH_OUTCOME_REQUESTS;
        };
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface UserType {
        Type value() default Type.EMPTY;

        enum Type {
            EMPTY, WITH_FRIENDS, WITH_INCOME_REQUEST, WITH_OUTCOME_REQUEST
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) {

        Map<UserType, StaticUser> usersMap = new HashMap<>();

        List<Parameter> parameters = Arrays.stream(context.getRequiredTestMethod().getParameters())
                        .filter(parameter -> AnnotationSupport.isAnnotated(parameter, UserType.class))
                                .toList();

        parameters.forEach(parameter -> {
            UserType userType = parameter.getAnnotation(UserType.class);
            Queue<StaticUser> queue = getQueueByType(userType.value());
            Optional<StaticUser> user = Optional.empty();
            StopWatch sw = StopWatch.createStarted();

            while (user.isEmpty() && sw.getTime(TimeUnit.SECONDS) < 30) {
                user = Optional.ofNullable(queue.poll());
            }

            user.ifPresentOrElse(
                    u -> {
                usersMap.put(userType, u);
            },
                    () -> {throw  new IllegalStateException("User type " + userType + " not found");}        );

        });

        context.getStore(NAMESPACE).put(context.getUniqueId(), usersMap);

        Allure.getLifecycle().updateTestCase(testCase ->
                testCase.setStart(new Date().getTime()));

    }

    @Override
    public void afterEach(ExtensionContext context) {
        Map<UserType,StaticUser> map = context.getStore(NAMESPACE).get(context.getUniqueId(), Map.class);

        for (Map.Entry<UserType, StaticUser> entry : map.entrySet()) {
            StaticUser user = entry.getValue();
            UserType userType = entry.getKey();
            System.out.println("Returning back to queue: " + user);
            Queue<StaticUser> queue = getQueueByType(userType.value());
            queue.add(user);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(StaticUser.class)
                && AnnotationSupport.isAnnotated(parameterContext.getParameter(), UserType.class);
    }

    @Override
    public StaticUser resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Map<UserType, StaticUser> map = extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), Map.class);
        return map.get(parameterContext.getParameter().getAnnotation(UserType.class));
    }
}
