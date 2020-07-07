package com.aionemu.gameserver.utils.mui;

import com.aionemu.commons.scripting.classlistener.ClassListener;
import com.aionemu.commons.utils.ClassUtils;
import com.aionemu.gameserver.utils.mui.handlers.MuiHandler;
import java.lang.reflect.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MuiHandlerClassListener implements ClassListener {

    private static final Logger log = LoggerFactory.getLogger(MuiHandlerClassListener.class);

    @SuppressWarnings("unchecked")
    @Override
    public void postLoad(Class<?>[] classes) {
        for (Class<?> c : classes) {
            if (log.isDebugEnabled()) {
                log.debug("Load class " + c.getName());
            }

            if (!isValidClass(c)) {
                continue;
            }

            if (ClassUtils.isSubclass(c, MuiHandler.class)) {
                Class<? extends MuiHandler> tmp = (Class<? extends MuiHandler>) c;
                if (tmp != null) {
                    MuiEngine.getInstance().addMuiHandlerClass(tmp);
                }
            }
        }
    }

    @Override
    public void preUnload(Class<?>[] classes) {
        if (log.isDebugEnabled()) {
            for (Class<?> c : classes) {
                log.debug("Unload class " + c.getName());
            }
        }
    }

    public boolean isValidClass(Class<?> clazz) {
        final int modifiers = clazz.getModifiers();

        if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
            return false;
        }

        if (!Modifier.isPublic(modifiers)) {
            return false;
        }

        return true;
    }

}