package br.com.loja.application;

import br.com.loja.model.dao.DaoFactory;
import br.com.loja.model.dao.DepartamentosDao;
import br.com.loja.model.entities.Departamentos;

import java.util.List;
import java.util.Scanner;

public class ProgramDepartamentos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        DepartamentosDao departamentosDao = DaoFactory.createDepartamentosDao();

        System.out.println("\n=== TEST 0: findAll =======");
        List<Departamentos> list = departamentosDao.findAll();
        for (Departamentos d : list) {
            System.out.println(d);
        }
        System.out.println("\n=== TEST 1: insert =======");
        System.out.print("Insira o nome do Departamento: ");
        String nome = sc.nextLine();
        Departamentos newDepartamentos = new Departamentos(null, nome);
        departamentosDao.insert(newDepartamentos);
        System.out.println("Inserted! New id: " + newDepartamentos.getId());

        System.out.println("\n=== TEST 2: update =======");
        System.out.print("Insira o id do departamento a alterar: ");
        int id1 = sc.nextInt();
        sc.nextLine();
        System.out.print("Insira o novo nome do Departamento: ");
        String nome1 = sc.nextLine();
        Departamentos dep2 = departamentosDao.findById(id1);
        dep2.setNome(nome1);
        departamentosDao.update(dep2);
        System.out.println("Update completed");

        System.out.println("=== TEST 3: findById =======");
        System.out.print("Digite o id do departamento: ");
        int id2 = sc.nextInt();
        Departamentos dep = departamentosDao.findById(id2);
        System.out.println(dep);


        System.out.println("\n=== TEST 4: delete =======");
        System.out.print("Entre com o id para excluir o departamento: ");
        int id = sc.nextInt();
        departamentosDao.deleteById(id);
        System.out.println("Delete completed");

        sc.close();
    }
}
