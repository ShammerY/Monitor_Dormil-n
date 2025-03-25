package model;

import java.util.concurrent.Semaphore;
import java.util.Random;

public class Estudiante extends Thread {
    private int id;
    private Semaphore semMonitor;
    private Semaphore semSillas;
    private Monitor monitor;

    public Estudiante(int id, Semaphore semMonitor, Semaphore semSillas, Monitor monitor) {
        this.id = id;
        this.semMonitor = semMonitor;
        this.semSillas = semSillas;
        this.monitor = monitor;
    }

    public int getIdEstudiante() {
        return id;
    }

    public void run() {
        try {
            // System.out.println("Estudiante " + id + " está programando...");
            Thread.sleep(new Random().nextInt(10000)); // Simula el tiempo programando
    
            System.out.println("Estudiante " + id + " busca ayuda del monitor.");
            
            if (semSillas.tryAcquire()) { // Si hay silla disponible
                System.out.println("Estudiante " + id + " se sienta en el corredor.");
                monitor.agregarEstudiante(this);
            } else {
                System.out.println("Estudiante " + id + " no encontró silla, regresa más tarde.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
