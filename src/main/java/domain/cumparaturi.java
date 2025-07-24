package domain;

import java.util.Objects;

public class cumparaturi extends Entity{
    private String marca,nume;
    private int pret,cantitate;
    public cumparaturi(int id,String marca, String nume, int pret, int cantitate) {
        super(id);
        this.marca = marca;
        this.nume = nume;
        this.pret = pret;
        this.cantitate = cantitate;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
    public int getPret() {
        return pret;
    }
    public void setPret(int pret) {
        this.pret = pret;
    }
    public int getCantitate() {
        return cantitate;
    }
    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        cumparaturi that = (cumparaturi) o;
        return pret == that.pret && cantitate == that.cantitate && Objects.equals(marca, that.marca) && Objects.equals(nume, that.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marca, nume, pret, cantitate);
    }

    @Override
    public String toString() {
        return super.getId()+ " "+marca + ','+ nume + ','+ pret +","+ cantitate;
    }
}
