package guru.qa.niffler.api.categories;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CategoriesApi {

    @POST("/internal/categories/add")
    Call<CategoryJson> addCategory(@Body CategoryJson Category);

    @GET("/internal/categories/all")
    Call<List<CategoryJson>> getCategories(@Query("username") String username,
                                           @Query("excludeArchived") boolean excludeArchived);

    @PATCH("/internal/categories/update")
    Call <CategoryJson> updateCategory(@Body CategoryJson Category);
}
