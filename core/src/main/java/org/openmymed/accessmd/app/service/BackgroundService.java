/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmymed.accessmd.app.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.reflections.Reflections;

/**
 *
 * @author tareq
 */
@Log
public abstract class BackgroundService {

    public abstract void start();

    public abstract void stop();
    private static final List<BackgroundService> SERVICES = new ArrayList<>();

    public static final void startBackgroundServices() {
        
        Reflections r = new Reflections("org.openmymed");
        for (Class clazz : r.getTypesAnnotatedWith(Service.class)) {
            try {
                Object o = clazz.getConstructor().newInstance();
                if (o instanceof BackgroundService) {
                    ((BackgroundService) o).start();
                    SERVICES.add((BackgroundService) o);
                    log.log(Level.INFO, "Started Background Service : {0}", clazz.getSimpleName());
                }
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }

    public static final void stopBackgroundServices() {
        SERVICES.forEach(service -> service.stop());
    }

}
