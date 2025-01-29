package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model;

import java.io.Serializable;

public class NotasCurso implements Serializable {
    private static final long serialVersionUID = 1L;
    private double nota1;
    private double nota2;
    private double nota3;
    private double nota4;
    private double promedio;
    private String idEstudiante;
    private String codigoCurso;

    public NotasCurso() {

    }

    public NotasCurso(String idEstudiante, String codigoCurso) {
        this.idEstudiante = idEstudiante;
        this.codigoCurso = codigoCurso;
        this.nota1 = 0.0;
        this.nota2 = 0.0;
        this.nota3 = 0.0;
        this.nota4 = 0.0;
        this.promedio = 0.0;
    }

    public double getNota1() {
        return nota1;
    }

    public void setNota1(double nota1) {
        this.nota1 = nota1;
        calcularPromedio();
    }

    public double getNota2() {
        return nota2;
    }

    public void setNota2(double nota2) {
        this.nota2 = nota2;
        calcularPromedio();
    }

    public double getNota3() {
        return nota3;
    }

    public void setNota3(double nota3) {
        this.nota3 = nota3;
        calcularPromedio();
    }

    public double getNota4() {
        return nota4;
    }

    public void setNota4(double nota4) {
        this.nota4 = nota4;
        calcularPromedio();
    }

    public double getPromedio() {
        return promedio;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    private void calcularPromedio() {
        this.promedio = (nota1 + nota2 + nota3 + nota4) / 4.0;
    }
}