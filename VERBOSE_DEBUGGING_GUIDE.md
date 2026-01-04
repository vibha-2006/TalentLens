# 🔍 Verbose Debugging Enabled - Deployment Troubleshooting Guide

## 🎯 What Was Added

### Dockerfile - Verbose Logging at Every Stage

The Dockerfile has been updated with extensive debugging output to track every step of the build process.

### Stage 1: Frontend Build
```dockerfile
✅ Node/npm version info
✅ Package file listing
✅ package.json content display
✅ npm ci with --verbose flag
✅ node_modules size check
✅ Source file listing
✅ Build output verification
✅ Build size reporting
```

### Stage 2: Backend Build
```dockerfile
✅ Java/Maven version info
✅ pom.xml content preview
✅ Maven dependency download with -X (debug)
✅ Java source file count
✅ Frontend static resources verification
✅ Maven build with -X (debug)
✅ JAR file verification
✅ JAR size reporting
```

### Stage 3: Runtime
```dockerfile
✅ Java version info
✅ User creation verification
✅ JAR file presence check
✅ File permissions display
✅ Runtime environment info
✅ Verbose GC logging
✅ Command line flags display
```

---

## 📊 render.yaml - Enhanced Logging

Added environment variables for verbose Spring Boot logging:

```yaml
LOGGING_LEVEL_ORG_SPRINGFRAMEWORK: DEBUG
LOGGING_LEVEL_ORG_EXAMPLE: DEBUG
JAVA_OPTS: "-verbose:gc -XX:+PrintCommandLineFlags -Dlogging.level.root=INFO"
```

---

## 🔍 What You'll See in Render Logs

### Build Process Logs

#### Stage 1 Output:
```
==== STAGE 1: Frontend Build ====
Node version: v18.x.x
npm version: 10.x.x
Working directory: /app/frontend

==== Copied package files ====
-rw-r--r-- package.json
-rw-r--r-- package-lock.json

==== package.json content ====
{
  "name": "talentlens-frontend",
  "version": "0.1.0",
  ...
}

==== Running npm ci ====
npm info it worked if it ends with ok
npm info using npm@10.x.x
npm info using node@v18.x.x
... (detailed npm output)

==== Dependencies installed ====
drwxr-xr-x node_modules/react
drwxr-xr-x node_modules/axios
... (top 20 modules)
Total node_modules size: 300M

==== Building React app ====
> talentlens-frontend@0.1.0 build
> react-scripts build
... (build output)

==== Build output ====
-rw-r--r-- asset-manifest.json
-rw-r--r-- index.html
drwxr-xr-x static/
Build size: 2.1M
```

#### Stage 2 Output:
```
==== STAGE 2: Backend Build ====
Java version: 17.x.x
Maven version: 3.9.x
Working directory: /app

==== pom.xml copied ====
-rw-r--r-- pom.xml

==== Downloading Maven dependencies ====
[DEBUG] ... (detailed Maven output)

==== Source code structure ====
src/main/java/org/example/Main.java
src/main/java/org/example/controller/ResumeController.java
... (all Java files)
Total Java files: XX

==== Frontend copied to static resources ====
drwxr-xr-x src/main/resources/static/
Static resources size: 2.1M
Files in static:
src/main/resources/static/index.html
src/main/resources/static/static/js/main.xxx.js
... (top 20 files)

==== Building Spring Boot application ====
[DEBUG] ... (detailed Maven build output)

==== Build artifacts ====
-rw-r--r-- target/TalentLens-1.0-SNAPSHOT.jar
JAR size: 50M
```

#### Stage 3 Output:
```
==== STAGE 3: Runtime Container ====
Java version: 17.x.x
Working directory: /app

==== Creating non-root user ====
User spring created

==== JAR file in runtime ====
-rw-r--r-- app.jar
JAR size: 50M

==== Runtime setup complete ====
User: spring
Home: /home/spring
Working dir: /app
-rw-r--r-- app.jar
```

---

## 🔍 How to Diagnose Issues

### 1. npm ci Fails (Stage 1)

**Look for**:
```
==== Running npm ci ====
npm ERR! code EUSAGE
```

**Check**:
- package.json content is displayed
- package-lock.json exists
- npm version compatibility

**Solution**:
- Verify package-lock.json is in sync
- Check npm version (should be 8+)
- Look for EUSAGE, ELOCKVERIFY errors

---

### 2. React Build Fails (Stage 1)

**Look for**:
```
==== Building React app ====
Error: ... (build error)
```

**Check**:
- node_modules installed correctly
- Build size is reasonable (~2MB)
- No missing dependencies

**Solution**:
- Check for TypeScript errors
- Verify all imports resolve
- Look for ESLint errors

---

### 3. Maven Dependency Download Fails (Stage 2)

**Look for**:
```
==== Downloading Maven dependencies ====
[ERROR] Failed to execute goal
```

**Check**:
- pom.xml is valid XML
- Internet connectivity
- Repository URLs are accessible

**Solution**:
- Check for invalid dependencies
- Verify repository URLs
- Look for version conflicts

---

### 4. Maven Build Fails (Stage 2)

**Look for**:
```
==== Building Spring Boot application ====
[ERROR] COMPILATION ERROR
```

**Check**:
- Java source file count
- Frontend static resources copied
- Compilation errors in Java code

**Solution**:
- Check for missing imports
- Verify all controllers exist
- Look for syntax errors

---

### 5. Frontend Not Embedded (Stage 2)

**Look for**:
```
==== Frontend copied to static resources ====
ls: cannot access 'src/main/resources/static/': No such file or directory
```

**Check**:
- Stage 1 build completed successfully
- build/ directory exists in Stage 1
- COPY command succeeded

**Solution**:
- Verify frontend build/ directory
- Check COPY --from command
- Ensure build artifacts exist

---

### 6. JAR Not Created (Stage 2)

**Look for**:
```
==== Build artifacts ====
ls: cannot access 'target/*.jar': No such file or directory
```

**Check**:
- Maven build completed
- No compilation errors
- target/ directory exists

**Solution**:
- Check Maven build logs
- Verify pom.xml packaging
- Look for plugin errors

---

### 7. Runtime Fails to Start (Stage 3)

**Look for** in runtime logs:
```
Error: Unable to access jarfile app.jar
```

**Check**:
- JAR copied from Stage 2
- File permissions correct
- User has access

**Solution**:
- Verify COPY --from command
- Check file ownership
- Ensure JAR is executable

---

## 🎯 Specific Error Patterns to Watch For

### Pattern 1: npm ci Still Failing

```
==== Running npm ci ====
npm ERR! code EUSAGE
npm ERR! `npm ci` can only install packages when your package.json 
and package-lock.json are in sync.
```

**Diagnosis**: Lock file still out of sync
**Action**: Verify the package-lock.json commit (1af7928) is in the build

---

### Pattern 2: Frontend Build Succeeds But Empty

```
==== Build output ====
total 0
```

**Diagnosis**: Build completed but no files generated
**Action**: Check react-scripts version and build script

---

### Pattern 3: Static Resources Not Found

```
==== Frontend copied to static resources ====
ls: cannot access: No such file or directory
```

**Diagnosis**: Stage 1 didn't complete or build/ missing
**Action**: Check Stage 1 completion, verify build/ directory

---

### Pattern 4: Maven Can't Find Dependencies

```
[ERROR] Failed to execute goal on project TalentLens: 
Could not resolve dependencies
```

**Diagnosis**: Repository connection issue or invalid dependency
**Action**: Check pom.xml dependencies, verify internet access

---

### Pattern 5: Out of Memory

```
[ERROR] Java heap space
```

**Diagnosis**: Build ran out of memory
**Action**: Reduce build scope or contact Render support

---

## 📋 Debugging Checklist

When reviewing logs, check each stage:

### Stage 1: Frontend Build
- [ ] Node/npm versions displayed
- [ ] package.json shown
- [ ] package-lock.json exists
- [ ] npm ci completes without EUSAGE
- [ ] node_modules/ created (~300MB)
- [ ] npm run build succeeds
- [ ] build/ directory created (~2MB)

### Stage 2: Backend Build
- [ ] Java/Maven versions displayed
- [ ] pom.xml shown
- [ ] Maven dependencies download
- [ ] Java files found and counted
- [ ] Frontend static resources copied
- [ ] Maven build succeeds
- [ ] JAR file created (~50MB)

### Stage 3: Runtime
- [ ] Java version displayed
- [ ] Non-root user created
- [ ] JAR file present in /app
- [ ] File permissions correct
- [ ] Application starts

---

## 🔧 Quick Fixes Based on Logs

### If you see: "EUSAGE"
```bash
cd frontend
rm package-lock.json
npm install
git add package-lock.json
git commit -m "Regenerate lock file"
git push
```

### If you see: "Build failed in Stage 1"
```bash
cd frontend
npm install
npm run build
# Check for errors, fix them, then:
git add .
git commit -m "Fix frontend build"
git push
```

### If you see: "Maven compilation error"
```bash
# Check Java files for errors
mvn clean compile
# Fix errors, then:
git add .
git commit -m "Fix backend compilation"
git push
```

### If you see: "Static resources not found"
- Verify frontend build/ directory exists locally
- Check .dockerignore doesn't exclude build/
- Ensure COPY command syntax is correct

---

## 📊 Expected Build Times with Verbose Logging

| Stage | Without Verbose | With Verbose | Difference |
|-------|-----------------|--------------|------------|
| Stage 1 | 1-2 min | 2-3 min | +30-60 sec |
| Stage 2 | 3-5 min | 4-7 min | +1-2 min |
| Stage 3 | 30 sec | 1 min | +30 sec |
| **Total** | **5-8 min** | **7-11 min** | **+2-3 min** |

The extra time is due to verbose output being written to logs.

---

## 🎯 What to Look for in Your Current Build

Based on the error you showed, watch for:

1. **npm ci command** - Should NOT show `--only=production`
2. **package-lock.json** - Should show 387KB size
3. **npm ci output** - Should complete without EUSAGE
4. **node_modules** - Should be ~300MB
5. **build/ directory** - Should be created and ~2MB

---

## 🚀 After Pushing These Changes

Render will rebuild with full verbose output. You'll see:

```
=== Build Log ===
Cloning repository...
✅ Commit: ef6b103 (or later)

==== STAGE 1: Frontend Build ====
[... detailed output ...]

==== STAGE 2: Backend Build ====
[... detailed output ...]

==== STAGE 3: Runtime Container ====
[... detailed output ...]

Deploying...
✅ Service is live!
```

---

## 📝 How to Share Logs for Support

If issues persist, copy these sections from Render logs:

1. **Error message** (the red ERROR line)
2. **Stage where it failed** (Stage 1, 2, or 3)
3. **Previous 20 lines** before error
4. **Versions displayed** (Node, npm, Java, Maven)
5. **File sizes** (package-lock.json, node_modules, build/)

---

## 🔍 Advanced Debugging

### Check Specific File in Build:

Add to Dockerfile after COPY:
```dockerfile
RUN cat path/to/file
```

### Check Network Connectivity:

Add to Dockerfile:
```dockerfile
RUN ping -c 3 registry.npmjs.org
RUN curl -I https://repo.maven.apache.org
```

### Check Disk Space:

Add to Dockerfile:
```dockerfile
RUN df -h
```

---

## ✅ Current Status

**Changes Made**:
1. ✅ Added verbose logging to Stage 1 (Frontend)
2. ✅ Added debug output to Stage 2 (Backend)
3. ✅ Added runtime diagnostics to Stage 3
4. ✅ Updated render.yaml with logging env vars
5. ✅ Ready to commit and push

**Next Steps**:
1. Commit these changes
2. Push to GitHub
3. Monitor Render logs with detailed output
4. Identify exact failure point
5. Apply targeted fix

---

**Status**: ✅ Verbose debugging enabled
**Impact**: +2-3 minutes build time, but full visibility
**Benefit**: Can pinpoint exact failure location

## 🎯 Let's Deploy and Debug!

