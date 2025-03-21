/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Model.DTO.Payment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author mndkh
 */
public class BankService {

    private static final String url = "https://script.google.com/macros/s/AKfycbwszkcRhYXvn7t6az0uwr6ibLEe5_Bm1SLyzkTcL7Ux98qmKvXU-H4QdfGD3ZQmgGxr/exec";

    public ArrayList<Payment> fetchData() throws Exception {
        ArrayList<Payment> list = new ArrayList<>();
        Gson gson = new Gson();
        Type typeObj = new TypeToken<ArrayList<Payment>>() {
        }.getType();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                list = gson.fromJson(response.body().string(), typeObj);
            } else {
                System.out.println("Lỗi: " + response.code());
            }
        } catch (Exception e) {
            throw e;
        }
        return list;
    }

    public Payment getPaymentByOID(int oid) throws Exception {
        Payment payment = null;
        ArrayList<Payment> payments = fetchData();
        for(Payment p : payments) {
            if(p.getOid() == oid) {
                payment = p;
                break;
            }
        }
        return payment;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new BankService().getPaymentByOID(8));
    }
}
