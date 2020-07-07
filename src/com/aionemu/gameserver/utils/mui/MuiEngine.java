package com.aionemu.gameserver.utils.mui;

import com.aionemu.commons.scripting.classlistener.AggregatedClassListener;
import com.aionemu.commons.scripting.classlistener.OnClassLoadUnloadListener;
import com.aionemu.commons.scripting.classlistener.ScheduledTaskClassListener;
import com.aionemu.commons.scripting.scriptmanager.ScriptManager;
import com.aionemu.gameserver.GameServerError;
import com.aionemu.gameserver.model.GameEngine;
import com.aionemu.gameserver.utils.mui.handlers.GeneralMuiHandler;
import com.aionemu.gameserver.utils.mui.handlers.MuiHandler;
import com.aionemu.gameserver.utils.mui.handlers.MuiName;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MuiEngine implements GameEngine {

    private static final Logger log = LoggerFactory.getLogger(MuiEngine.class);
    private static ScriptManager scriptManager = new ScriptManager();
    public static final File MUI_DESCRIPTOR_FILE = new File("./data/scripts/system/muihandlers.xml");
    public static final MuiHandler DUMMY_MUI_HANDLER = new GeneralMuiHandler();
    private FastMap<String, Class<? extends MuiHandler>> handlers = new FastMap<String, Class<? extends MuiHandler>>().shared();

    @Override
    public void load(CountDownLatch progressLatch) {
        log.info("Mui engine load started");
        scriptManager = new ScriptManager();

        AggregatedClassListener acl = new AggregatedClassListener();
        acl.addClassListener(new OnClassLoadUnloadListener());
        acl.addClassListener(new ScheduledTaskClassListener());
        acl.addClassListener(new MuiHandlerClassListener());
        scriptManager.setGlobalClassListener(acl);

        try {
            scriptManager.load(MUI_DESCRIPTOR_FILE);
            log.info("Loaded " + handlers.size() + " mui handlers.");
        } catch (Exception e) {
            throw new GameServerError("Can't initialize mui handlers.", e);
        } finally {
            if (progressLatch != null) {
                progressLatch.countDown();
            }
        }
    }

    @Override
    public void shutdown() {
        log.info("Mui engine shutdown started");
        scriptManager.shutdown();
        scriptManager = null;
        handlers.clear();
        log.info("Mui engine shutdown complete");
    }

    public MuiHandler getNewMuiHandler(String eventName) {
        Class<? extends MuiHandler> instanceClass = handlers.get(eventName);
        MuiHandler instanceHandler = null;
        if (instanceClass != null) {
            try {
                instanceHandler = instanceClass.newInstance();
            } catch (Exception ex) {
                log.warn("Can't instantiate event handler " + eventName, ex);
            }
        }
        if (instanceHandler == null) {
            instanceHandler = DUMMY_MUI_HANDLER;
        }
        return instanceHandler;
    }

    /**
     * @param handler
     */
    final void addMuiHandlerClass(Class<? extends MuiHandler> handler) {
        MuiName nameAnnotation = handler.getAnnotation(MuiName.class);
        if (nameAnnotation != null) {
            handlers.put(nameAnnotation.value(), handler);
        }
    }

    public FastMap<String, Class<? extends MuiHandler>> getHendlers() {
        return handlers;
    }

    public static final MuiEngine getInstance() {
        return SingletonHolder.instance;
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final MuiEngine instance = new MuiEngine();
    }
}
