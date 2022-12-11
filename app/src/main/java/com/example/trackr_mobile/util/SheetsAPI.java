package com.example.trackr_mobile.util;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsRequestInitializer;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SheetsAPI {
    private static final String APPLICATION_NAME = "trackr_mobile";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final Context context;

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */

    public SheetsAPI(Context context) {
        this.context = context;
    }


    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void run() {
        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(context, Collections.singleton(SheetsScopes.SPREADSHEETS));
        credential.setSelectedAccount(credential.getAllAccounts()[0]);
        // Build a new authorized API client service.
        final String apiKey = "AIzaSyBPJmfyTPfRPGV566hxysCCkv3H8TscVJQ";
        final NetHttpTransport HTTP_TRANSPORT = new com.google.api.client.http.javanet.NetHttpTransport();
        final String spreadsheetId = "1tHZnpezywsKwHmdCtDfXIBZ-Yn7MmliK4VI9_he9i7Q";
        final String range = "A1:E6";
        SheetsRequestInitializer sheetsRequestInitializer = new SheetsRequestInitializer(apiKey);
        Sheets service =
                null;
        service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .setGoogleClientRequestInitializer(sheetsRequestInitializer)
                .build();

        ValueRange response = null;
        try {
            response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .setMajorDimension("ROWS")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<Object>> value = new ArrayList<>();
        value.add(List.of("test", "test1", "test2", "test3", "test4"));
        ValueRange updates = new ValueRange().setValues(value);
        UpdateValuesResponse updateValuesResponse;
        try {
            updateValuesResponse = service.spreadsheets().values()
                    .update(spreadsheetId, "Sheet1!A2:E2", updates)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                row.stream().forEach((item) -> {
                    System.out.print(item + " ");
                });
                System.out.println("");
            }
        }
    }
}