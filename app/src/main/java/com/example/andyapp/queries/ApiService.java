package com.example.andyapp.queries;

import com.example.andyapp.models.CategoryAllocation;
import com.example.andyapp.models.FetchIncome;
import com.example.andyapp.models.LeaderboardUser;
import com.example.andyapp.models.NotificationResponse;
import com.example.andyapp.models.PostCategoryAllocation;
import com.example.andyapp.models.PostIncome;
import com.example.andyapp.queries.mongoModels.Expense;
import com.example.andyapp.queries.mongoModels.LoginModel;
import com.example.andyapp.queries.mongoModels.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("expenses/post")
    Call<ResponseBody> createExpense(@Body Expense expense);

    @GET("expenses/user/{userId}/total-by-category")
    Call<HashMap<String, Double>> getTotalExpensesByCategory(@Path("userId") String userId);

    @GET("/users/login")
    Call<UserModel> getUser(@Body LoginModel login);

    @GET("expenses/user/{userId}")
    Call<List<Expense>> getUserExpenses(@Header("Authorization") String token,@Path("userId") String userId);

    @GET("incomes/user/{userId}/year/{year}/month/{month}")
    Call<ArrayList<FetchIncome>>getIncomeForMonth(@Path("userId") String userId,@Path("year") int year, @Path("month")int month);

    @POST("incomes")
    Call<ResponseBody>postIncome(@Body PostIncome data);

    @GET("users/{userId}/notifications")
    Call<List<NotificationResponse>> getNotifications(
            @Header("Authorization") String token,
            @Path("userId") String userId
    );
    //Get unread notifications
    @GET("users/{userId}/notifications/unread-count")
    Call<Integer> getUnreadCount(
            @Header("Authorization") String token,
            @Path("userId") String userId
    );
    //Send nudge notification
    @POST("users/{fromUsername}/nudge/{toUsername}")
    Call<ResponseBody>sendNudge(@Path("fromUsername") String forUsername, @Path("toUsername") String toUsername);
    //Get all outgoing connections
    @GET("users/{userId}/connections")
    Call<ArrayList<Map<String, String>>> getConnections(
            @Path("userId") String userId
    );

    //Get budget by category
    @GET("category-allocations/user/{userId}")
    Call<ArrayList<CategoryAllocation>>getCategoryAllocations(@Path("userId") String userId);

    //Log a new Budget
    @POST("category-allocations")
    Call<ResponseBody>updateBudget(@Body ArrayList<PostCategoryAllocation> dataModels);

    //Invite a connection
    @POST("users/{inviterUsername}/invite/{inviteeUsername}")
    Call<ResponseBody>inviteConnection(@Path("inviterUsername") String inviterId, @Path("inviteeUsername") String inviteeId);

    //Get Pending Invitations
    @GET("users/{userId}/pending-invitations")
    Call<ArrayList<Map<String, String>>>getInvitations(@Path("userId") String userId);

    //Respond to an Invitation
    @FormUrlEncoded
    @PUT("users/{inviteeUsername}/respond-invitation/{inviterUsername}")
    Call<ResponseBody>respondInvitation(@Path("inviteeUsername") String inviteeUsername, @Path("inviterUsername") String inviterUsername, @Field("accept") boolean accept);

    //Delete a connection
    @DELETE("users/{userId}/connections/{connectionId}")
    Call<ResponseBody>removeConnection(@Path("userId") String userId, @Path("connectionId") String connectionId);

    @GET("users/{userId}/score")
    Call<Integer> getUserScore(
            @Header("Authorization") String token,
            @Path("userId") String userId
    );

    @GET("users/{userId}/get-leaderboard")
    Call<List<LeaderboardUser>> getLeaderboard(@Path("userId") String userId);


}


