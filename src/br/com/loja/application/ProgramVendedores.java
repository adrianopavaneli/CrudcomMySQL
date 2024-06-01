package br.com.loja.application;

import br.com.loja.model.dao.DaoFactory;
import br.com.loja.model.dao.VendedoresDao;
import br.com.loja.model.entities.Departamentos;
import br.com.loja.model.entities.Vendedores;


import javax.swing.text.DateFormatter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ProgramVendedores {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");


        Scanner sc = new Scanner(System.in);
        VendedoresDao vendedoresDao = DaoFactory.createVendedoresDao();
        Departamentos departamentos = null;
        List<Vendedores> list = null;
        Vendedores vendedores = null;

        System.out.println("==========Teste0 - FindAll===========");
        departamentos = new Departamentos(2, null);
        list = vendedoresDao.findByDepartamentos(departamentos);
        list = vendedoresDao.findAll();
        for (Vendedores obj : list) {
            System.out.println(obj);
        }
        System.out.println("\n=== TEST 1: vendedores insert =====");
        System.out.print("Digite o nome do vendedor: ");
        String nome = sc.nextLine();
        System.out.print("Digite o email do vendedor: ");
        String email = sc.nextLine();

        System.out.print("Digite a data de aniversário do vendedor(aaaa-MM-dd): ");
        String dataString = sc.nextLine();
        LocalDate data = LocalDate.parse(dataString, formatter);

        System.out.print("Digite o salario base do vendedor: ");
        double salario = sc.nextDouble();
        System.out.print("Digite o id do departamento do vendedor: ");
        int departamento = sc.nextInt();
        Vendedores newVendedores = new Vendedores(null, nome, email, data, salario, new Departamentos(departamento,null));
        vendedoresDao.insert(newVendedores);
        System.out.println("Inserted! New id = " + newVendedores.getId());

        System.out.println("\n=== TEST 2: vendedores update =====");
        System.out.print("Digite o id do vendedor a alterar: ");
        int id3 = sc.nextInt();
        sc.nextLine();
        vendedores = vendedoresDao.findById(id3);
        System.out.print("Digite o nome do vendedor: ");
        String nome3 = sc.nextLine();
        vendedores.setNome(nome3);
        System.out.print("Digite o email do vendedor: ");
        String email3 = sc.nextLine();
        vendedores.setEmail(email3);
        System.out.print("Digite a data de aniversário do vendedor(aaaa-MM-dd): ");
        String dataString1 = sc.nextLine();
        LocalDate data3 = LocalDate.parse(dataString, formatter);
        vendedores.setDataAniversario(data3);
        System.out.print("Digite o salario base do vendedor: ");
        double salario3 = sc.nextDouble();
        vendedores.setSalarioBase(salario3);
        System.out.print("Digite o id do departamento do vendedor: ");
        int departamento3 = sc.nextInt();
        vendedores.setDepartamentos(new Departamentos(departamento3, null));
        vendedoresDao.update(vendedores);
        System.out.println("Update completed");

        System.out.println("==========Teste3 - FindById===========");
        vendedores = vendedoresDao.findById(3);
        System.out.println(vendedores);


        System.out.println("==========Teste4 - findByDepartamentos===========");
        for (Vendedores obj : list) {
            System.out.println(obj);
        }




        System.out.println("\n=== TEST 5: vendedores delete =====");
        System.out.println("Enter id for delete test: ");
        int id = sc.nextInt();
        vendedoresDao.deleteById(id);
        System.out.println("Delete completed");

        sc.close();
    }

}