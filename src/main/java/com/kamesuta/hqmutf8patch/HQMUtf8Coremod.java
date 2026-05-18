package com.kamesuta.hqmutf8patch;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

public final class HQMUtf8Coremod implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        return new String[] { DataReaderUtf8Transformer.class.getName() };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
