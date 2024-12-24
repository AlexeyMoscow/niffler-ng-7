package guru.qa.niffler.api.categories;

import guru.qa.niffler.model.CategoryJson;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

import static org.apache.hc.core5.http.HttpStatus.SC_CREATED;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoriesApiClient {

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8093/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private final CategoriesApi categoryApi = retrofit.create(CategoriesApi.class);

    public CategoryJson addCategory(CategoryJson category)  {
        final Response <CategoryJson> response;
        try {
            response = categoryApi.addCategory(category).execute();
        }
        catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(SC_OK, response.code());
        return response.body();
    }

    public CategoryJson updateCategory(CategoryJson category) {
        final Response<CategoryJson> response;
        try {
            response = categoryApi.updateCategory(category)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(SC_OK, response.code());
        return response.body();
    }


    public List<CategoryJson> getCategories(String username, boolean excludedArchived) {
        Response<List<CategoryJson>> response;
        try {
            response = categoryApi.getCategories(username, excludedArchived).execute();
        }
        catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(SC_OK, response.code());
        return response.body();
    }
}
