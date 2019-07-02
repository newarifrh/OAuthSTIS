package dev.blank.oauthstis;

class UtilsApi {


    // Mendeklarasikan Interface BaseApiService
    static BaseApiService getAPIService() {

        return NetworkHandler.getRetrofit().create(BaseApiService.class);
    }


    private static String getBaseUrlApi() {
        return "https://ws.stis.ac.id/";
    }




}
