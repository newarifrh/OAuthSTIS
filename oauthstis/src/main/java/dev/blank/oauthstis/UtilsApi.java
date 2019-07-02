package dev.blank.oauthstis;

public class UtilsApi {


    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(getBaseUrlApi()).create(BaseApiService.class);
    }


    private static String getBaseUrlApi() {
        return "http://ws.stis.ac.id/";
    }


}
