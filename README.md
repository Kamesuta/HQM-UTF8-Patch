# HQM-UTF8-Patch

A small Forge 1.7.10 coremod for HQM.

## Purpose

This patch makes HQM read `.hqm` quest files as UTF-8, instead of relying on the platform default charset.

## What it changes

- Target: `hardcorequesting.network.DataReader#readString`
- Replacement: `new String(bytes)` → `new String(bytes, StandardCharsets.UTF_8)`

## Build

```bash
./gradlew build
```

Output:

- `build/libs/HQM-UTF8-Patch-1.0.jar`

## Install

Put the jar in the same mods folder as HQM.
