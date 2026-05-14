# String Encoder Tool (Base64 + XOR)

A small Java utility for obfuscating strings with XOR and Base64 so they are not directly visible in decompiled code.

⚠️ This is not cryptographic security. It is simple obfuscation only.

---

# 📦 Requirements

- Java 8 or higher
- `encoder.jar`

---

# 🚀 Usage

## CLI mode

Encode a string from the command line:

```bash
java -jar encoder.jar <input> <xor_key>
```

The `<xor_key>` can be a single character (for example `K`) or a number (`75`).

Example:

```bash
java -jar encoder.jar "https://example.com/site" K
```

## GUI mode

Run without arguments to open the graphical encoder:

```bash
java -jar encoder.jar
```

Enter the string and XOR key, then click **Generate**. Use **Copy Generated String** to copy the result.

---

# 📌 Output

The tool prints or shows an encoded string like:

```
Q1d9c3d2c3h0...
```

Use that value in your Java code.

---

# 🔧 Decode pattern

Use this pattern to decode the string in Java:

```java
byte[] decoded = java.util.Base64.getDecoder().decode(enc);
String xored = new String(decoded);
char[] out = new char[xored.length()];
for (int i = 0; i < xored.length(); i++) {
    out[i] = (char) (xored.charAt(i) ^ key);
}
String result = new String(out);
```

---

# ⚠️ Limitations

- Not a secure encryption method
- XOR key is visible in code
- Base64 is easy to decode
- Runtime values can be inspected in a debugger

---

# 🧠 Recommendation

Use this only for low-risk obfuscation. For stronger protection, apply code obfuscation tools such as ProGuard or dedicated string encryption libraries.
```