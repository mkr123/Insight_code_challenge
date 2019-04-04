package PruchaseAnalytics;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;


public class readCvs {

    public static void main(String[] args) {
        List<Order> orderState = new ArrayList<>();
        HashMap<String, Integer> productHm = new HashMap<String, Integer>();
        HashMap<Integer, SaleNumber> departmentHm = new HashMap<Integer, SaleNumber>();
        ArrayList<Integer> unsortedDep = new ArrayList<>();
        int depNumber;
        int depsaleNumber;
        int firstorderNumber;
        double dNum;
        double fNum;
        double percent;
        //
        try {
            BufferedReader reader = new BufferedReader(new FileReader("D:\\WebDEV\\Purchase-Analytics\\untitled\\input\\order_products.csv"));//换成你的文件名
            reader.readLine();
            String line = null;

            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//CS
                Order order = new Order();
                order.productID = item[1];
                order.addtoCart = Integer.parseInt(item[2]);
                if (item[3].compareTo("0") == 0) {
                    order.firsttimeOrder = Integer.parseInt(item[2]);
                } else {
                    order.firsttimeOrder = 0;
                }

                orderState.add(order);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        //Read From products.csv
        try {
            BufferedReader reader = new BufferedReader(new FileReader("D:\\WebDEV\\Purchase-Analytics\\untitled\\input\\products.csv"));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//CS
                productHm.put(item[0], Integer.parseInt(item[3]));
                if (!(unsortedDep.contains(Integer.parseInt(item[3])))) {
                    unsortedDep.add(Integer.parseInt(item[3]));
                }
                //System.out.println(unsortedDep);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Add department number to each Order object
        for (int i = 0; i < orderState.size(); i++) {
            String selectedID = orderState.get(i).productID;
            if (productHm.containsKey(selectedID)) {
                orderState.get(i).DepartmentNumber = productHm.get(selectedID);
            }
        }
        //put salenumber and firsttimeorder Number into hashmap which key is department number
        for (int i = 0; i < orderState.size(); i++) {
            SaleNumber saleNumber = new SaleNumber();
            int selectedDep = orderState.get(i).DepartmentNumber;
            if (departmentHm.containsKey(selectedDep) == false) {
                saleNumber.addcartNumber = orderState.get(i).addtoCart;
                saleNumber.firsttimeOrder = orderState.get(i).firsttimeOrder;
                departmentHm.put(selectedDep, saleNumber);
            } else {
                departmentHm.get(selectedDep).firsttimeOrder += orderState.get(i).firsttimeOrder;
                departmentHm.get(selectedDep).addcartNumber += orderState.get(i).addtoCart;

            }



        }
        Collections.sort(unsortedDep);


        try {
            File csv; // CSV数据文件
            csv = new File("D:\\WebDEV\\Purchase-Analytics\\untitled\\output\\report.csv");

            BufferedWriter bw = new BufferedWriter(new FileWriter(csv));
            // 添加新的数据行
            bw.write("department_id" + "," + "number_of_orders" + "，" + "number_of_first_orders" + "," +"percentage");
            bw.newLine();
            for(int i = 0;i<unsortedDep.size();i++ ){
                depNumber = unsortedDep.get(i);
                depsaleNumber = departmentHm.get(unsortedDep.get(i)).addcartNumber;
                firstorderNumber = departmentHm.get(unsortedDep.get(i)).firsttimeOrder;
                dNum = departmentHm.get(unsortedDep.get(i)).addcartNumber;
                fNum = departmentHm.get(unsortedDep.get(i)).firsttimeOrder;
                DecimalFormat df = new DecimalFormat("0.00");
                percent = fNum/dNum;
                System.out.println(depsaleNumber);
                System.out.println(firstorderNumber);
                System.out.println(percent);
                bw.write( depNumber+ "," + depsaleNumber+ "," + firstorderNumber + "," + df.format(percent)
                );
                bw.newLine();
            }
            bw.close();

        } catch (FileNotFoundException e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        } catch (IOException e) {
            // BufferedWriter在关闭对象捕捉异常
            e.printStackTrace();
        }
    }


    }







