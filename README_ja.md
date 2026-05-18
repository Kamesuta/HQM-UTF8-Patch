# HQM-UTF8-Patch

HQM 向けの小さな Forge 1.7.10 Coremod です。

## 目的

HQM の `.hqm` クエストファイル読み込み時に、プラットフォーム既定文字コードではなく UTF-8 固定で解釈します。

## 変更点

- 対象: `hardcorequesting.network.DataReader#readString`
- 置換: `new String(bytes)` → `new String(bytes, StandardCharsets.UTF_8)`

## ビルド

```bash
./gradlew build
```

生成物:

- `build/libs/HQM-UTF8-Patch-1.0.jar`

## 配置

HQM と同じ mods フォルダに入れてください。
