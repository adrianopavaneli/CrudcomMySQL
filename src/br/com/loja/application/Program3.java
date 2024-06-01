package br.com.loja.application;

import br.com.loja.model.dao.AgendaDao;
import br.com.loja.model.dao.DaoFactory;
import br.com.loja.model.entities.Agenda;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AgendaDao agendaDao = DaoFactory.createAgendaDao();
        Date d01 = new Date();

        System.out.println("==== TEST0 - findAll");
        List<Agenda> list  = agendaDao.findAll();
        for (Agenda d : list){
            System.out.println(d);
        }

        System.out.println("=== TEST1 - insert ========");
        System.out.print("Insira o nome: ");
        String nome = sc.nextLine();
        System.out.print("Insira a idade: ");
        int idade = sc.nextInt();
        Agenda newAgenda = new Agenda(null, nome,idade, d01);
        agendaDao.insert(newAgenda);
        System.out.println("Inserido! Novo id: " + newAgenda.getId());

        System.out.println("=== TEST2 - update =========");
        System.out.print("Digite o id a ser atualizado: ");
        int id1 = sc.nextInt();
        sc.nextLine();
        System.out.print("Digite o novo nome: ");
        String nome1 = sc.nextLine();
        System.out.print("Digite a nova idade: ");
        int idade1 = sc.nextInt();;
        Agenda agenda2 = agendaDao.findById(id1);
        agenda2.setNome(nome1);
        agenda2.setIdade(idade1);
        agendaDao.update(agenda2);
        System.out.println("Cadastro Atualizado");

        System.out.println("=== TEST3 - FindByid");
        System.out.print("Digite o id da agenda: ");
        int id = sc.nextInt();
        Agenda agenda = agendaDao.findById(id);
        System.out.println(agenda);
        System.out.println("Cadastro Atualizado!");

        System.out.println("===TEST4: delete =====");
        System.out.print("Entre com o id a ser deletado: ");
        int id3 = sc.nextInt();
        agendaDao.deleteById(id3);
        System.out.println("Registro deletado!");
        sc.close();
    }
}
