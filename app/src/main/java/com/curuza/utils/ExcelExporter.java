package com.curuza.utils;


import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.curuza.data.client.Client;
import com.curuza.data.credit.Credit;
import com.curuza.data.depense.Depense;
import com.curuza.data.fournisseur.Fournisseur;
import com.curuza.data.stock.Product;
import com.curuza.data.view.ProductMovement;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;



public class ExcelExporter  {

    private Context mContext;

    public ExcelExporter(Context mContext){
        this.mContext = mContext;
    }

    public static void exportProducts(Context mContext,List<Product> productList){

        Workbook wb = new HSSFWorkbook();

        Cell cell = null;

        Sheet sheet = null;
        sheet = wb.createSheet("excel product list");
        //Now column and row
        Row row = sheet.createRow(0);


        cell = row.createCell(0);
        cell.setCellValue("Name");


        cell = row.createCell(1);
        cell.setCellValue("Quantity");


        cell = row.createCell(2);
        cell.setCellValue("Prix achat");

        cell = row.createCell(3);
        cell.setCellValue("Prix de vente");


        //column width
        sheet.setColumnWidth(0, (20 * 200));
        sheet.setColumnWidth(1, (30 * 200));
        sheet.setColumnWidth(2, (30 * 200));
        sheet.setColumnWidth(3, (30 * 200));


        for (int i = 0; i < productList.size(); i++) {
            Row row1 = sheet.createRow(i + 1);

            cell = row1.createCell(0);
            cell.setCellValue(productList.get(i).getName());

            cell = row1.createCell(1);
            cell.setCellValue((productList.get(i).getQuantity()));
            //  cell.setCellStyle(cellStyle);

            cell = row1.createCell(2);
            cell.setCellValue(productList.get(i).getPAchat());

            cell = row1.createCell(3);
            cell.setCellValue(productList.get(i).getPVente());


            sheet.setColumnWidth(0, (20 * 200));
            sheet.setColumnWidth(1, (30 * 200));
            sheet.setColumnWidth(2, (30 * 200));
            sheet.setColumnWidth(3, (30 * 200));

        }
        String folderName = "Curuza Import Excel";
        String fileName = "list of products " + System.currentTimeMillis() + ".xls";
        String path = Environment.getExternalStorageDirectory() + File.separator + folderName + File.separator + fileName;

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + folderName);
        if (!file.exists()) {
            file.mkdirs();
        }

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(path);
            wb.write(outputStream);
            // ShareViaEmail(file.getParentFile().getName(),file.getName());
            Toast.makeText(mContext, "Excel Created in " + path, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Not OK", Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
    }

    public static void exportAllDocuments(Context mContext, List<ProductMovement>productMovements){
        Workbook wb = new HSSFWorkbook();

        Cell cell = null;

        Sheet sheet = null;
        sheet = wb.createSheet("excel Movement list");
        //Now column and row
        Row row = sheet.createRow(0);


        cell = row.createCell(0);
        cell.setCellValue("Date");


        cell = row.createCell(1);
        cell.setCellValue("Name");


        cell = row.createCell(2);
        cell.setCellValue("Quantity");

        cell = row.createCell(3);
        cell.setCellValue("Status");

        cell = row.createCell(4);
        cell.setCellValue("Prix Achat");

        cell = row.createCell(5);
        cell.setCellValue("Prix Vente");


        //column width
        sheet.setColumnWidth(0, (20 * 200));
        sheet.setColumnWidth(1, (30 * 200));
        sheet.setColumnWidth(2, (30 * 200));
        sheet.setColumnWidth(3, (30 * 200));
        sheet.setColumnWidth(4, (30 * 200));
        sheet.setColumnWidth(5, (30 * 200));


        for (int i = 0; i < productMovements.size(); i++) {
            Row row1 = sheet.createRow(i + 1);

            cell = row1.createCell(0);
            cell.setCellValue(productMovements.get(i).getMovement().getDate());

            cell = row1.createCell(1);
            cell.setCellValue((productMovements.get(i).getProduct().getName()));
            //  cell.setCellStyle(cellStyle);

            cell = row1.createCell(2);
            cell.setCellValue(productMovements.get(i).getMovement().getQuantity());

            cell = row1.createCell(3);
            cell.setCellValue(productMovements.get(i).getMovement().getStatus().toString());

            cell = row1.createCell(4);
            cell.setCellValue(productMovements.get(i).getMovement().getPAchat());

            cell = row1.createCell(5);
            cell.setCellValue(productMovements.get(i).getMovement().getPVente());


            sheet.setColumnWidth(0, (20 * 200));
            sheet.setColumnWidth(1, (30 * 200));
            sheet.setColumnWidth(2, (30 * 200));
            sheet.setColumnWidth(3, (30 * 200));
            sheet.setColumnWidth(4, (30 * 200));
            sheet.setColumnWidth(5, (30 * 200));

        }
        String folderName = "Curuza Import Excel";
        String fileName = "list of Documents " + System.currentTimeMillis() + ".xls";
        String path = Environment.getExternalStorageDirectory() + File.separator + folderName + File.separator + fileName;

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + folderName);
        if (!file.exists()) {
            file.mkdirs();
        }

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(path);
            wb.write(outputStream);
            // ShareViaEmail(file.getParentFile().getName(),file.getName());
            Toast.makeText(mContext, "Excel Created in " + path, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Not OK", Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
    }

    public static void exportEnterDocuments(Context mContext, List<ProductMovement>enterproductMovements){
        Workbook wb = new HSSFWorkbook();

        Cell cell = null;

        Sheet sheet = null;
        sheet = wb.createSheet("excel Movement list");
        //Now column and row
        Row row = sheet.createRow(0);


        cell = row.createCell(0);
        cell.setCellValue("Date");


        cell = row.createCell(1);
        cell.setCellValue("Name");


        cell = row.createCell(2);
        cell.setCellValue("Quantity");

        cell = row.createCell(3);
        cell.setCellValue("Status");

        cell = row.createCell(4);
        cell.setCellValue("Prix Achat");

        cell = row.createCell(5);
        cell.setCellValue("Prix Vente");


        //column width
        sheet.setColumnWidth(0, (20 * 200));
        sheet.setColumnWidth(1, (30 * 200));
        sheet.setColumnWidth(2, (30 * 200));
        sheet.setColumnWidth(3, (30 * 200));
        sheet.setColumnWidth(4, (30 * 200));
        sheet.setColumnWidth(5, (30 * 200));


        for (int i = 0; i < enterproductMovements.size(); i++) {
            Row row1 = sheet.createRow(i + 1);

            cell = row1.createCell(0);
            cell.setCellValue(enterproductMovements.get(i).getMovement().getDate());

            cell = row1.createCell(1);
            cell.setCellValue((enterproductMovements.get(i).getProduct().getName()));
            //  cell.setCellStyle(cellStyle);

            cell = row1.createCell(2);
            cell.setCellValue(enterproductMovements.get(i).getMovement().getQuantity());

            cell = row1.createCell(3);
            cell.setCellValue(enterproductMovements.get(i).getMovement().getStatus().toString());

            cell = row1.createCell(4);
            cell.setCellValue(enterproductMovements.get(i).getMovement().getPAchat());

            cell = row1.createCell(5);
            cell.setCellValue(enterproductMovements.get(i).getMovement().getPVente());


            sheet.setColumnWidth(0, (20 * 200));
            sheet.setColumnWidth(1, (30 * 200));
            sheet.setColumnWidth(2, (30 * 200));
            sheet.setColumnWidth(3, (30 * 200));
            sheet.setColumnWidth(4, (30 * 200));
            sheet.setColumnWidth(5, (30 * 200));

        }
        String folderName = "Curuza Import Excel";
        String fileName = "list of enter movements " + System.currentTimeMillis() + ".xls";
        String path = Environment.getExternalStorageDirectory() + File.separator + folderName + File.separator + fileName;

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + folderName);
        if (!file.exists()) {
            file.mkdirs();
        }

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(path);
            wb.write(outputStream);
            // ShareViaEmail(file.getParentFile().getName(),file.getName());
            Toast.makeText(mContext, "Excel Created in " + path, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Not OK", Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
    }

    public static void exportExitDocuments(Context mContext, List<ProductMovement>exitproductMovements){
        Workbook wb = new HSSFWorkbook();

        Cell cell = null;

        Sheet sheet = null;
        sheet = wb.createSheet("excel Movement list");
        //Now column and row
        Row row = sheet.createRow(0);


        cell = row.createCell(0);
        cell.setCellValue("Date");


        cell = row.createCell(1);
        cell.setCellValue("Name");


        cell = row.createCell(2);
        cell.setCellValue("Quantity");

        cell = row.createCell(3);
        cell.setCellValue("Status");

        cell = row.createCell(4);
        cell.setCellValue("Prix Achat");

        cell = row.createCell(5);
        cell.setCellValue("Prix Vente");


        //column width
        sheet.setColumnWidth(0, (20 * 200));
        sheet.setColumnWidth(1, (30 * 200));
        sheet.setColumnWidth(2, (30 * 200));
        sheet.setColumnWidth(3, (30 * 200));
        sheet.setColumnWidth(4, (30 * 200));
        sheet.setColumnWidth(5, (30 * 200));


        for (int i = 0; i < exitproductMovements.size(); i++) {
            Row row1 = sheet.createRow(i + 1);

            cell = row1.createCell(0);
            cell.setCellValue(exitproductMovements.get(i).getMovement().getDate());

            cell = row1.createCell(1);
            cell.setCellValue((exitproductMovements.get(i).getProduct().getName()));
            //  cell.setCellStyle(cellStyle);

            cell = row1.createCell(2);
            cell.setCellValue(exitproductMovements.get(i).getMovement().getQuantity());

            cell = row1.createCell(3);
            cell.setCellValue(exitproductMovements.get(i).getMovement().getStatus().toString());

            cell = row1.createCell(4);
            cell.setCellValue(exitproductMovements.get(i).getMovement().getPAchat());

            cell = row1.createCell(5);
            cell.setCellValue(exitproductMovements.get(i).getMovement().getPVente());


            sheet.setColumnWidth(0, (20 * 200));
            sheet.setColumnWidth(1, (30 * 200));
            sheet.setColumnWidth(2, (30 * 200));
            sheet.setColumnWidth(3, (30 * 200));
            sheet.setColumnWidth(4, (30 * 200));
            sheet.setColumnWidth(5, (30 * 200));

        }
        String folderName = "Curuza Import Excel";
        String fileName = "list of exit movements " + System.currentTimeMillis() + ".xls";
        String path = Environment.getExternalStorageDirectory() + File.separator + folderName + File.separator + fileName;

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + folderName);
        if (!file.exists()) {
            file.mkdirs();
        }

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(path);
            wb.write(outputStream);
            // ShareViaEmail(file.getParentFile().getName(),file.getName());
            Toast.makeText(mContext, "Excel Created in " + path, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Not OK", Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
    }

    public static void exportCredits(Context mContext,List<Credit> credits){

        Workbook wb = new HSSFWorkbook();

        Cell cell = null;

        Sheet sheet = null;
        sheet = wb.createSheet("excel credit list");
        //Now column and row
        Row row = sheet.createRow(0);


        cell = row.createCell(0);
        cell.setCellValue("Name");


        cell = row.createCell(1);
        cell.setCellValue("Telephone Number");


        cell = row.createCell(2);
        cell.setCellValue("Amount");




        //column width
        sheet.setColumnWidth(0, (20 * 200));
        sheet.setColumnWidth(1, (30 * 200));
        sheet.setColumnWidth(2, (30 * 200));



        for (int i = 0; i < credits.size(); i++) {
            Row row1 = sheet.createRow(i + 1);

            cell = row1.createCell(0);
            cell.setCellValue(credits.get(i).getPersonName());

            cell = row1.createCell(1);
            cell.setCellValue((credits.get(i).getTelephone()));
            //  cell.setCellStyle(cellStyle);

            cell = row1.createCell(2);
            cell.setCellValue(credits.get(i).getAmount());




            sheet.setColumnWidth(0, (20 * 200));
            sheet.setColumnWidth(1, (30 * 200));
            sheet.setColumnWidth(2, (30 * 200));


        }
        String folderName = "Curuza Import Excel";
        String fileName = "list of credits " + System.currentTimeMillis() + ".xls";
        String path = Environment.getExternalStorageDirectory() + File.separator + folderName + File.separator + fileName;

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + folderName);
        if (!file.exists()) {
            file.mkdirs();
        }

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(path);
            wb.write(outputStream);
            // ShareViaEmail(file.getParentFile().getName(),file.getName());
            Toast.makeText(mContext, "Excel Created in " + path, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Not OK", Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
    }

    public static void exportDepenses(Context mContext,List<Depense> depenses){

        Workbook wb = new HSSFWorkbook();

        Cell cell = null;

        Sheet sheet = null;
        sheet = wb.createSheet("excel depense list");
        //Now column and row
        Row row = sheet.createRow(0);


        cell = row.createCell(0);
        cell.setCellValue("Date");


        cell = row.createCell(1);
        cell.setCellValue("Description");


        cell = row.createCell(2);
        cell.setCellValue("Amount");




        //column width
        sheet.setColumnWidth(0, (20 * 200));
        sheet.setColumnWidth(1, (30 * 200));
        sheet.setColumnWidth(2, (30 * 200));



        for (int i = 0; i < depenses.size(); i++) {
            Row row1 = sheet.createRow(i + 1);

            cell = row1.createCell(0);
            cell.setCellValue(depenses.get(i).getDate());

            cell = row1.createCell(1);
            cell.setCellValue((depenses.get(i).getDescription()));
            //  cell.setCellStyle(cellStyle);

            cell = row1.createCell(2);
            cell.setCellValue(depenses.get(i).getAmount());




            sheet.setColumnWidth(0, (20 * 200));
            sheet.setColumnWidth(1, (30 * 200));
            sheet.setColumnWidth(2, (30 * 200));


        }
        String folderName = "Curuza Import Excel";
        String fileName = "list of depenses " + System.currentTimeMillis() + ".xls";
        String path = Environment.getExternalStorageDirectory() + File.separator + folderName + File.separator + fileName;

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + folderName);
        if (!file.exists()) {
            file.mkdirs();
        }

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(path);
            wb.write(outputStream);
            // ShareViaEmail(file.getParentFile().getName(),file.getName());
            Toast.makeText(mContext, "Excel Created in " + path, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Not OK", Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
    }

    public static void exportFournisseurs(Context mContext,List<Fournisseur> fournisseurs){

        Workbook wb = new HSSFWorkbook();

        Cell cell = null;

        Sheet sheet = null;
        sheet = wb.createSheet("excel fournisseurs list");
        //Now column and row
        Row row = sheet.createRow(0);


        cell = row.createCell(0);
        cell.setCellValue("Nom");


        cell = row.createCell(1);
        cell.setCellValue("Telephone Number");


        cell = row.createCell(2);
        cell.setCellValue("Description");




        //column width
        sheet.setColumnWidth(0, (20 * 200));
        sheet.setColumnWidth(1, (30 * 200));
        sheet.setColumnWidth(2, (30 * 200));



        for (int i = 0; i < fournisseurs.size(); i++) {
            Row row1 = sheet.createRow(i + 1);

            cell = row1.createCell(0);
            cell.setCellValue(fournisseurs.get(i).getPersonName());

            cell = row1.createCell(1);
            cell.setCellValue((fournisseurs.get(i).getTelephone()));
            //  cell.setCellStyle(cellStyle);

            cell = row1.createCell(2);
            cell.setCellValue(fournisseurs.get(i).getDescription());




            sheet.setColumnWidth(0, (20 * 200));
            sheet.setColumnWidth(1, (30 * 200));
            sheet.setColumnWidth(2, (30 * 200));


        }
        String folderName = "Curuza Import Excel";
        String fileName = "list of fournisseurs " + System.currentTimeMillis() + ".xls";
        String path = Environment.getExternalStorageDirectory() + File.separator + folderName + File.separator + fileName;

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + folderName);
        if (!file.exists()) {
            file.mkdirs();
        }

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(path);
            wb.write(outputStream);
            // ShareViaEmail(file.getParentFile().getName(),file.getName());
            Toast.makeText(mContext, "Excel Created in " + path, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Not OK", Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
    }

    public static void exportClients(Context mContext,List<Client> clients){

        Workbook wb = new HSSFWorkbook();

        Cell cell = null;

        Sheet sheet = null;
        sheet = wb.createSheet("excel fournisseurs list");
        //Now column and row
        Row row = sheet.createRow(0);


        cell = row.createCell(0);
        cell.setCellValue("Nom");


        cell = row.createCell(1);
        cell.setCellValue("Telephone Number");


        cell = row.createCell(2);
        cell.setCellValue("Description");




        //column width
        sheet.setColumnWidth(0, (20 * 200));
        sheet.setColumnWidth(1, (30 * 200));
        sheet.setColumnWidth(2, (30 * 200));



        for (int i = 0; i < clients.size(); i++) {
            Row row1 = sheet.createRow(i + 1);

            cell = row1.createCell(0);
            cell.setCellValue(clients.get(i).getPersonName());

            cell = row1.createCell(1);
            cell.setCellValue((clients.get(i).getTelephone()));
            //  cell.setCellStyle(cellStyle);

            cell = row1.createCell(2);
            cell.setCellValue(clients.get(i).getDescription());




            sheet.setColumnWidth(0, (20 * 200));
            sheet.setColumnWidth(1, (30 * 200));
            sheet.setColumnWidth(2, (30 * 200));


        }
        String folderName = "Curuza Import Excel";
        String fileName = "list of clients " + System.currentTimeMillis() + ".xls";
        String path = Environment.getExternalStorageDirectory() + File.separator + folderName + File.separator + fileName;

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + folderName);
        if (!file.exists()) {
            file.mkdirs();
        }

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(path);
            wb.write(outputStream);
            // ShareViaEmail(file.getParentFile().getName(),file.getName());
            Toast.makeText(mContext, "Excel Created in " + path, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Not OK", Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
    }
}
