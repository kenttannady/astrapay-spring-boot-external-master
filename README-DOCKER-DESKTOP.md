# Docker Desktop Deployment Guide

## 🚀 Simple CI/CD untuk Docker Desktop

### 📋 Prerequisites
- Docker Desktop dengan Kubernetes enabled
- kubectl terinstall dan terkonfigurasi
- GitHub repository dengan workflow

### 🔄 Cara Pakai

**Option 1: Otomatis via GitHub Actions**
```bash
# Push ke develop branch
git checkout develop
git add .
git commit -m "Deploy to Docker Desktop"
git push origin develop
```

**Option 2: Manual Trigger**
1. Buka GitHub repository
2. Pergi ke "Actions" tab
3. Pilih "Docker Desktop Deploy" workflow
4. Klik "Run workflow" button

**Option 3: Script Manual**
```bash
# Jalankan deployment script langsung
kubectl apply -k k8s/
kubectl rollout status deployment/astrapay-app -n astrapay
kubectl port-forward service/astrapay-service 8000:8000 -n astrapay
```

### 📊 Workflow Features
- ✅ Build otomatis Docker image
- ✅ Push ke GitHub Container Registry
- ✅ Deploy ke Docker Desktop Kubernetes
- ✅ Health check verification
- ✅ Pod status monitoring

### 🌐 Akses Aplikasi

Setelah deployment selesai:
```bash
# Port forward untuk akses lokal
kubectl port-forward service/astrapay-service 8000:8000 -n astrapay

# Buka browser
http://localhost:8000

# Test endpoints
curl http://localhost:8000/actuator/health
curl "http://localhost:8000/hello?name=DockerDesktop&description=Test"
```

### 🔧 Troubleshooting

**Pod tidak running:**
```bash
kubectl get pods -n astrapay
kubectl describe pod <pod-name> -n astrapay
```

**Deployment gagal:**
```bash
kubectl rollout status deployment/astrapay-app -n astrapay
kubectl rollout undo deployment/astrapay-app -n astrapay
```

**Service tidak accessible:**
```bash
kubectl get services -n astrapay
kubectl logs deployment/astrapay-app -n astrapay
```

### 📁 File Structure
```
.github/workflows/
├── docker-desktop-deploy.yml    # Workflow sederhana
k8s/
├── namespace.yaml              # Namespace
├── configmap.yaml             # Konfigurasi
├── secret.yaml                # Database credentials
├── app-deployment.yaml         # Spring Boot app
├── postgres-deployment.yaml    # Database
└── redis-deployment.yaml        # Cache
```

### 🎯 Best Practices
- Gunakan develop branch untuk development
- Gunakan main branch untuk production
- Monitor pod health dan resource usage
- Gunakan image tags yang spesifik (bukan latest)
- Implement proper logging dan monitoring
