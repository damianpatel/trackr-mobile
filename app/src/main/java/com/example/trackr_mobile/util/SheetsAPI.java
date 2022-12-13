package com.example.trackr_mobile.util;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.trackr_mobile.model.Application;
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
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class SheetsAPI {
    private static final String APPLICATION_NAME = "trackr_mobile";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final Context context;
    final String apiKey = "AIzaSyBPJmfyTPfRPGV566hxysCCkv3H8TscVJQ";
    final NetHttpTransport HTTP_TRANSPORT = new com.google.api.client.http.javanet.NetHttpTransport();
    private final String spreadsheetId;
    private Sheets service;

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     * @param context
     */

    public SheetsAPI(Context context, String spreadsheetId) {
        this.context = context;
        this.spreadsheetId = spreadsheetId;
        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(context, Collections.singleton(SheetsScopes.SPREADSHEETS));
        credential.setSelectedAccount(credential.getAllAccounts()[0]);
        service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Application> getAllEntries() {
        // Build a new authorized API client service.
        final String range = "A2:E";
        ValueRange response = null;
        try {
            response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .setMajorDimension("ROWS")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<Object>> values = response.getValues();
        List<Application> applications = new ArrayList<>();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values) {
                String company = (String) row.get(0);
                String title = (String) row.get(1);
                String startDate = (String) row.get(2);
                String dateApplied = (String) row.get(3);
                String status = (String) row.get(4);
                applications.add(new Application(company, title, startDate, dateApplied, status));
            }
        }
        return applications;
    }

    public void updateStatus(String status, String row) {
        List<List<Object>> value = new ArrayList<>();
        value.add(List.of(status));
        ValueRange updates = new ValueRange().setValues(value);
        UpdateValuesResponse updateValuesResponse;
        try {
            updateValuesResponse = service.spreadsheets().values()
                    .update(spreadsheetId, "Sheet1!" + row, updates)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}