# String Encoder Tool (Base64 + XOR)

Dieses Projekt stellt ein kleines Java-Tool bereit, mit dem du Strings (z. B. URLs, Tokens oder API-Keys) so encoden kannst, dass sie im dekompilierten Java-Code nicht mehr direkt lesbar sind.

Es handelt sich dabei um eine einfache Obfuscation-Methode bestehend aus:
- XOR-Verschlüsselung
- Base64-Encoding

⚠️ Wichtig: Das ist **keine echte kryptografische Sicherheit**, sondern nur Obfuscation gegen einfaches Reverse Engineering.

---

# 📦 Voraussetzungen

- Java 8 oder höher installiert
- `encoder.jar` (das gebaute Tool)

---

# 🚀 Verwendung des Encoder Tools

## ▶️ String encoden

Du übergibst einen String und einen XOR-Key als Argumente:

```bash
java -jar encoder.jar <input> <xor_key>
```

Der `<xor_key>` kann sein:
- Ein einzelner Character (z. B. `K`)
- Eine Zahl (z. B. `75`)

---

## 📌 Beispiele

Mit Character-Key:
```bash
java -jar encoder.jar "https://example.com/site" K
```

Mit numerischem Key:
```bash
java -jar encoder.jar "https://example.com/site" 75
```

---

## 📤 Ausgabe

Das Tool gibt dann z. B. folgendes aus:

```
Encoded string:
Q1d9c3d2c3h0...
```

Dieser String ist dein verschlüsselter Wert, den du später in deinem Java-Projekt verwendest.

---

# 🧠 Einbindung in ein Java-Projekt

## 🔐 1. Encoded String speichern

Du ersetzt die originale URL oder den String durch den Encoded-Wert:

```java
String enc = "Q1d9c3d2c3h0...";
char key = 'K';
```

---

## 🔓 2. Decoder (inline im Code)

Der Decoder wird direkt in deine Methode eingefügt, ohne extra Klassen:

```java
byte[] decoded = java.util.Base64.getDecoder().decode(enc);
String xored = new String(decoded);

char[] out = new char[xored.length()];
for (int i = 0; i < xored.length(); i++) {
    out[i] = (char)(xored.charAt(i) ^ key);
}

String result = new String(out);
```

---

## 🌐 3. Beispiel: Verwendung mit HttpURLConnection

```java
String enc = "Q1d9c3d2c3h0...";
char key = 'K';

byte[] decoded = java.util.Base64.getDecoder().decode(enc);
String xored = new String(decoded);

char[] out = new char[xored.length()];
for (int i = 0; i < xored.length(); i++) {
    out[i] = (char)(xored.charAt(i) ^ key);
}

String urlStr = new String(out);

@SuppressWarnings("deprecation")
HttpURLConnection conn =
        (HttpURLConnection) new URL(urlStr).openConnection();

conn.setRequestMethod("GET");
conn.connect();
```

---

# 🔐 Was wird dadurch erreicht?

✔ Kein Klartext-String im JAR  
✔ URL ist im Decompiled Code nicht direkt sichtbar  
✔ Hürde für einfache Reverse Engineering Tools  

---

# ⚠️ Wichtige Einschränkungen

Diese Methode ist nur Obfuscation:

- ❌ Kein echter Schutz gegen Reverse Engineering  
- ❌ XOR-Key ist im Code sichtbar  
- ❌ Base64 ist trivial dekodierbar  
- ❌ Runtime-Werte können im Debugger ausgelesen werden  

---

# 🧠 Sicherheitsniveau

| Methode | Schutz |
|----------|--------|
| Klartext String | ❌ keiner |
| Base64 | 🟡 sehr schwach |
| XOR + Base64 | 🟡 mittel |
| echte String Encryption Tools | 🟢 stark |

---

# 🚀 Empfehlung

Für bessere Obfuscation in Java Projekten:

- ProGuard für Code Obfuscation  
- Stringer / Allatori für echte String Encryption  
- Kombination im Build Prozess (Gradle Task nach build)

---

# 🧪 Kurzworkflow

1. String encoden (mit deinem gewählten Key):
```bash
java -jar encoder.jar "dein string" K
```

2. Output kopieren

3. In Code einfügen:
```java
String enc = "...";
```

4. Decoder inline verwenden

---

# 🧠 Fazit

Dieses Tool dient dazu, Strings im Code unlesbar zu machen und einfache Decompiler zu verwirren. Es ersetzt keine echte Security, sondern erhöht nur den Aufwand für Reverse Engineering.
```