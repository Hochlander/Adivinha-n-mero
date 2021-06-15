/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import utils.FazConta;

/**
 *
 * @author anibal
 */
@ManagedBean
@SessionScoped
public class SorteioBean {

    private int sorteio;
    private int min;
    private int max;
    private Integer palpite;
    private int ultimoAcima;
    private int ultimoAbaixo;
    private int tentativas = 0;
    private boolean iniciado = false;
    private boolean certo = false;
    private String resultado;
    private LinkedList<Integer> maisList = new LinkedList<>();
    private LinkedList<Integer> menosList = new LinkedList<>();
    private LinkedList<Integer> palpitesList = new LinkedList<>();

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public int getSorteio() {
        return sorteio;
    }

    public void setSorteio(int sorteio) {
        this.sorteio = sorteio;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Integer getPalpite() {
        return palpite;
    }

    public void setPalpite(Integer palpite) {
        this.palpite = palpite;
    }

    public int getUltimoAcima() {
        return ultimoAcima;
    }

    public void setUltimoAcima(int ultimoAcima) {
        this.ultimoAcima = ultimoAcima;
    }

    public int getTentativas() {
        return tentativas;
    }

    public void setTentativas(int tentativas) {
        this.tentativas = tentativas;
    }

    public int getUltimoAbaixo() {
        return ultimoAbaixo;
    }

    public void setUltimoAbaixo(int ultimoAbaixo) {
        this.ultimoAbaixo = ultimoAbaixo;
    }

    public LinkedList<Integer> getPalpitesList() {
        return palpitesList;
    }

    public void setPalpitesList(LinkedList<Integer> palpitesList) {
        this.palpitesList = palpitesList;
    }

    public boolean isIniciado() {
        return iniciado;
    }

    public void setIniciado(boolean iniciado) {
        this.iniciado = iniciado;
    }

    public boolean isCerto() {
        return certo;
    }

    public void setCerto(boolean certo) {
        this.certo = certo;
    }

    public void geraNumero() {
        if (!palpitesList.isEmpty()) {
            reiniciar();
        }
        iniciado=true;
        certo=false;
        sorteio = ThreadLocalRandom.current().nextInt(min, max + 1);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Novo numero sorteado entre " + min + " e " + max));

    }

    public boolean pegaPalpite() throws IOException {

        tentativas++;
        palpitesList.add(palpite);

        if (palpite == sorteio) {
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('modalVitoria').show();");
            iniciado=false;
            certo=true;
            return true;
        }
        if (palpite < min || palpite > max) {
            resultado = ("tente de " + min + " a " + max);
        }
        if (palpite < sorteio && palpite >= min) {
            maisList.add(palpite);
            resultado = ("mais");
            ultimoAbaixo = palpite;
//            if (menosList.isEmpty()) {
//                System.out.println("ultimo abaixo: " + maisList. getLast());
//            }
//            if (!menosList.isEmpty()) {
//                System.out.println("ultimo acima: " + menosList.getLast() +",ultimo abaixo: " + maisList.getLast());
//            }
        }
        if (palpite > sorteio && palpite <= max) {
            menosList.add(palpite);
            resultado = ("menos");
            ultimoAcima = palpite;
//            if (maisList.isEmpty()) {
//                System.out.println("ultimo acima: " + menosList.getLast());
//            }
//            if (!maisList.isEmpty()) {
//                System.out.println("ultimo acima: " + menosList.getLast() +",ultimo abaixo: " + maisList.getLast());
//            }
        }
        palpite = null;
        return false;
    }

    public void reiniciar() {
        tentativas = 0;
        ultimoAbaixo = 0;
        ultimoAcima = 0;
        palpite = 0;
        resultado = "";
        maisList.clear();
        menosList.clear();
        palpitesList.clear();
    }

    public void viewProducts() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("viewProducts", options, null);
    }

    public static void main(String[] args) {
    }
}
