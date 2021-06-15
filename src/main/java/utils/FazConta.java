/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.LinkedList;
import beans.SorteioBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author anibal
 */
public class FazConta {

    private static int chute = 0;
    private static int tentativas = 0;
    private static LinkedList<Integer> chutesList = new LinkedList<Integer>();
    private static LinkedList<Integer> maisList = new LinkedList<Integer>();
    private static LinkedList<Integer> menosList = new LinkedList<Integer>();

    public boolean pegaChute(SorteioBean sorteado) throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("seu chute: ");
        // Reading data using readLine
        String palpite = reader.readLine();
        chute = Integer.parseInt(palpite);
        tentativas++;
        chutesList.add(chute);
        System.out.println("Tentativas realizadas: " + chutesList);

        System.out.println("ultimo chute: " + chute);

        System.out.println("Resultado para " + tentativas + "ª tentativa: ");

        if (chute == sorteado.getSorteio()) {
            System.out.println("boa!! Vc acertou apos " + tentativas + " tentativas");
            return true;
        }
        if (chute < sorteado.getMin() || chute > sorteado.getMax()) {
            System.out.println("tente de " + sorteado.getMin() + " a " + sorteado.getMax());
        }
        if (chute <= sorteado.getSorteio() && chute >= sorteado.getMin()) {
            maisList.add(chute);
            System.out.println("mais");
            if (menosList.isEmpty()) {
                System.out.println("ultimo abaixo: " + maisList.getLast());
            }
            if (!menosList.isEmpty()) {
                System.out.println("ultimo acima: " + menosList.getLast() +",ultimo abaixo: " + maisList.getLast());
            }
        }
        if (chute >= sorteado.getSorteio() && chute <= sorteado.getMax()) {
            menosList.add(chute);
            System.out.println("menos");
            if (maisList.isEmpty()) {
                System.out.println("ultimo acima: " + menosList.getLast());
            }
            if (!maisList.isEmpty()) {
                System.out.println("ultimo acima: " + menosList.getLast() +",ultimo abaixo: " + maisList.getLast());
            }
        }
        return false;
    }

    public int geraNumero(SorteioBean sore) {

        int numeroSorteado = ThreadLocalRandom.current().nextInt(sore.getMin(), sore.getMax() + 1);
        System.out.println("numero sorteado");
        return numeroSorteado;
    }

    public SorteioBean geraSorteioBean() throws IOException {
        SorteioBean sor = new SorteioBean();
        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("insira valor minimo: ");
        // Reading data using readLine
        String minimoString = reader.readLine();
        sor.setMin(Integer.parseInt(minimoString));

        System.out.println("insira valor maximo: ");
        // Reading data using readLine
        String maximoString = reader.readLine();
        sor.setMax(Integer.parseInt(maximoString));

        System.out.println("sorteando valor entre " + sor.getMin() + " e " + sor.getMax());
        return sor;
    }

    public static void main(String[] args) throws IOException {
        FazConta faz = new FazConta();
        SorteioBean ss = faz.geraSorteioBean();
        int numSorte = faz.geraNumero(ss);
        ss.setSorteio(numSorte);
        while (faz.pegaChute(ss) == false) {
            faz.pegaChute(ss);
        }
        System.out.close();
    }

}
