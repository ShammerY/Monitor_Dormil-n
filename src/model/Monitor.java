package model;

import java.util.concurrent.Semaphore;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Monitor extends Thread {
    private Semaphore semMonitor;
    private Semaphore semSillas;
    private Semaphore mutex;
    private Queue<Estudiante> colaEstudiantes;
    
    public Monitor(Semaphore semMonitor, Semaphore semSillas, Semaphore mutex) {
        this.semMonitor = semMonitor;
        this.semSillas = semSillas;
        this.mutex = mutex;
        this.colaEstudiantes = new LinkedList<>();
    }

    public void atenderEstudiante(Estudiante estudiante) {
        try {
            System.out.println("Monitor atendiendo al estudiante " + estudiante.getIdEstudiante());
            Thread.sleep(new Random().nextInt(2000)); // Simula tiempo de ayuda
            System.out.println("Monitor terminó con el estudiante " + estudiante.getIdEstudiante());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                semMonitor.acquire(); // Espera hasta que un estudiante lo despierte

                while (true) {
                    mutex.acquire(); // Bloquea acceso a la cola de estudiantes
                    if (colaEstudiantes.isEmpty()) {
                        System.out.println("Monitor se duerme...");
                        mutex.release();
                        break;
                    }
                    Estudiante estudiante = colaEstudiantes.poll(); // Toma al siguiente estudiante
                    mutex.release();

                    semSillas.release(); // Libera una silla
                    atenderEstudiante(estudiante);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void agregarEstudiante(Estudiante estudiante) {
        try {
            mutex.acquire();
            colaEstudiantes.add(estudiante);
            mutex.release();
            semMonitor.release(); // Despierta al monitor si está dormido
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}