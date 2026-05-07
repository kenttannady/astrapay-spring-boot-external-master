# AstraPay Spring Boot - Docker & Kubernetes Deployment Guide

## 📋 Prerequisites

- Docker Desktop (Windows/Mac) or Docker Engine (Linux)
- kubectl configured for Docker Desktop Kubernetes
- Maven 3.6+
- Java 11+

## 🚀 Quick Start with Docker Compose

### Step 1: Build and Run with Docker Compose

```bash
# Clone the repository
git clone <repository-url>
cd astrapay-spring-boot-external

# Build and run all services
docker-compose up --build

# Run in detached mode
docker-compose up -d --build
```

### Step 2: Verify Services

```bash
# Check running containers
docker-compose ps

# View application logs
docker-compose logs -f astrapay-app

# Test the application
curl http://localhost:8000/actuator/health
```

# Test the hello endpoint
curl "http://localhost:8000/hello?name=John&description=Test"

# Check health endpoint
curl http://localhost:8000/actuator/health

# View application info
curl http://localhost:8000/actuator/info


### Step 3: Stop Services

```bash
# Stop all services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

## ☸️ Kubernetes Deployment

### Step 1: Enable Kubernetes in Docker Desktop

1. Open Docker Desktop
2. Go to Settings → Kubernetes
3. Enable Kubernetes
4. Apply & Restart

### Step 2: Deploy to Kubernetes

```bash
# Apply all Kubernetes resources
kubectl apply -k k8s/

# Check deployment status
kubectl get pods -n astrapay
kubectl get services -n astrapay

# Watch pod status
kubectl get pods -n astrapay -w
```

### Step 3: Access the Application

```bash
# Port forward to local machine
kubectl port-forward service/astrapay-service 8000:8000 -n astrapay



# Access via localhost
curl http://localhost:8000/actuator/health

kubectl exec astrapay-app-54966c96dd-lwb2q -n astrapay -- curl -s http://localhost:8000/actuator/health

# Health check
kubectl exec -n astrapay deployment/astrapay-app -- curl http://localhost:8000/actuator/health

# Hello endpoint
kubectl exec -n astrapay deployment/astrapay-app -- curl "http://localhost:8000/hello?name=DevOps&description=Kubernetes+Test"
```

## 🔄 CI/CD Pipeline with GitHub Actions

### Required Secrets

Add these secrets to your GitHub repository:

1. `KUBE_CONFIG`: Base64 encoded kubeconfig file
   ```bash
   # Generate base64 kubeconfig
   cat ~/.kube/config | base64 -w 0
   ```

### Pipeline Triggers

- **Push to `main`**: Full CI/CD pipeline (test → build → deploy)
- **Push to `develop`**: CI pipeline only (test)
- **Pull Request**: CI pipeline only (test)

### Pipeline Stages

1. **Test**: Maven tests with caching
2. **Build**: Docker image build and push to GHCR
3. **Deploy**: Kubernetes deployment with Kustomize
4. **Security**: Trivy vulnerability scanning

## 📁 Project Structure

```
├── Dockerfile                    # Multi-stage build for Spring Boot
├── docker-compose.yml            # Local development setup
├── k8s/                          # Kubernetes manifests
│   ├── namespace.yaml            # Application namespace
│   ├── configmap.yaml            # Application configuration
│   ├── secret.yaml               # Database credentials
│   ├── postgres-deployment.yaml  # PostgreSQL deployment
│   ├── redis-deployment.yaml     # Redis cache deployment
│   ├── app-deployment.yaml       # Spring Boot app deployment
│   └── kustomization.yaml        # Kustomize configuration
├── .github/workflows/
│   └── ci-cd.yml                 # GitHub Actions pipeline
└── src/main/resources/
    └── application-docker.properties # Docker-specific config
```

## 🔧 Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Spring profile | `docker` |
| `JAVA_OPTS` | JVM options | `-Xms512m -Xmx1024m` |

### Database Configuration

- **PostgreSQL**: `postgres-db:5432/astrapay_db`
- **Redis**: `redis-cache:6379`
- **Username**: `astrapay_user`
- **Password**: `astrapay_password`

## 🔍 Monitoring & Health Checks

### Application Endpoints

- **Health**: `GET /actuator/health`
- **Info**: `GET /actuator/info`
- **Metrics**: `GET /actuator/metrics`

### Kubernetes Health Checks

- **Liveness Probe**: `/actuator/health` (60s initial, 30s interval)
- **Readiness Probe**: `/actuator/health` (30s initial, 10s interval)

## 🛠️ Troubleshooting

### Common Issues

1. **Port conflicts**: Ensure ports 8000, 5432, 6379 are available
2. **Memory issues**: Increase Docker Desktop memory allocation
3. **Kubernetes timeouts**: Check cluster resources and pod status

### Debug Commands

```bash
# Docker Compose logs
docker-compose logs astrapay-app

# Kubernetes pod logs
kubectl logs -f deployment/astrapay-app -n astrapay

# Describe pod issues
kubectl describe pod -l app=astrapay-app -n astrapay

# Port forward for debugging
kubectl port-forward deployment/astrapay-app 8080:8000 -n astrapay
```

## 📊 Resource Allocation

### Docker Compose

| Service | CPU | Memory |
|---------|-----|--------|
| astrapay-app | - | 512MB-1GB |
| postgres-db | - | - |
| redis-cache | - | - |

### Kubernetes

| Component | Requests | Limits |
|-----------|----------|--------|
| astrapay-app | 500m CPU, 512Mi RAM | 1000m CPU, 1Gi RAM |
| postgres | 250m CPU, 256Mi RAM | 500m CPU, 512Mi RAM |
| redis | 100m CPU, 128Mi RAM | 200m CPU, 256Mi RAM |

## 🔐 Security Considerations

- Non-root user in containers
- Secrets management with Kubernetes Secrets
- Health checks for all services
- Resource limits to prevent DoS
- Vulnerability scanning with Trivy
