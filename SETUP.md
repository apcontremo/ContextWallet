# ContextWallet - Configuración del Proyecto

## Problema Actual: Java 25 Incompatibilidad

El proyecto está configurado correctamente, pero **Android Studio necesita usar su JDK embebido** en lugar de Java 25 del sistema.

## Solución: Configurar Android Studio

### Paso 1: Cambiar el JDK en Android Studio

1. Abre **Android Studio**
2. Ve a **File → Settings** (o **Android Studio → Preferences** en Mac)
3. Navega a **Build, Execution, Deployment → Build Tools → Gradle**
4. En **Gradle JDK**, selecciona **jbr-17** (JetBrains Runtime 17) o cualquier JDK 17/21
   - Si no aparece, selecciona **Download JDK** y descarga **Temurin 17**

### Paso 2: Sincronizar el Proyecto

1. Haz clic en **File → Sync Project with Gradle Files**
2. O haz clic en el ícono de elefante 🐘 en la barra de herramientas

### Paso 3: Invalidar Cachés (si es necesario)

Si los errores persisten:
1. **File → Invalidate Caches**
2. Marca **Clear file system cache and Local History**
3. Haz clic en **Invalidate and Restart**

## Verificación

Después de estos pasos, los errores en rojo deberían desaparecer y el botón **Build** debería habilitarse.

## Alternativa: Instalar Java 17

Si prefieres usar Java 17 del sistema:

```bash
brew install openjdk@17
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
```

Luego reinicia Android Studio.
