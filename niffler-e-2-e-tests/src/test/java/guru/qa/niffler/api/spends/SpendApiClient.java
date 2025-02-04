package guru.qa.niffler.api.spends;

import guru.qa.niffler.model.SpendJson;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

import static org.apache.hc.core5.http.HttpStatus.SC_CREATED;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpendApiClient {
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8093/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private final SpendApi spendApi = retrofit.create(SpendApi.class);

    public SpendJson createSpend(SpendJson spend)  {
        final Response <SpendJson> response;
        try {
            response = spendApi.addSpend(spend).execute();
        }
        catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(SC_CREATED, response.code());
        return response.body();
    }

    public SpendJson editSpend(SpendJson spend) {
        final Response<SpendJson> response;
        try {
            response = spendApi.editSpend(spend)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(SC_OK, response.code());
        return response.body();
    }

    public SpendJson getSpend(String id, String username) {
        final Response<SpendJson> response;
        try{
            response = spendApi.getSpend(id, username)
                    .execute();
        }
        catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        return response.body();
    }

    public List<SpendJson> getSpends(String username) {
        Response<List<SpendJson>> response;
        try {
            response = spendApi.getSpends(username).execute();
        }
        catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(SC_OK, response.code());
        return response.body();
    }

    public void deleteSpends(String username, List<String> ids) {
        Response<Void> response;
        try {
            response = spendApi.deleteSpends(username, ids).execute();
        }
        catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(SC_OK, response.code());
    }


}
