# 🌱 AgriConnect
**AgriConnect** is a SaaS platform empowering farmers to **book tractors, harvesters, and labor**, with integrated **ML-based crop recommendation and yield prediction**, and a **Voice & Text AI Assistant chatbot** for real-time query support.

---

## 🚀 Features
- 📌 **Equipment Booking System** – Book tractors, harvesters, and labor from local farmers.  
- 📝 **Equipment Listing** – Farmers can list available resources for others to rent.  
- ❌ **Booking Cancellation & Service Completion APIs** – Smooth service lifecycle management.  
- 🔐 **Secure Access Control** – Powered by **Spring Security + JWT**.  
- 🤖 **AI Assistant (Voice + Text)** – Built using **FastAPI, LangChain, Pinecone, Groq LLM**.  
- 🌾 **ML Crop Recommendation** – Suggests suitable crops based on soil & weather.  
- 📊 **Yield Prediction** – Helps farmers estimate crop yield in advance.  
- 🗄 **Multi-Database Support** – **PostgreSQL (production)** + **SQLite (ML/Chatbot)**.  
- ⚡ **Scalable Microservices** – Core APIs with **Spring Boot**.  
- 🌐 **Responsive Frontend** – Built with **React + Redux**.  
- 🐳 **Containerized Deployment** – Runs with **Docker**, deployed on **Render Cloud**.  

---

## 🛠 Tech Stack
- **Backend:** Spring Boot, FastAPI, Spring Security, LangChain  
- **Databases:** PostgreSQL (production), SQLite (ML/Chatbot)  
- **Frontend:** React.js, Redux, Tailwind CSS  
- **AI/ML:** Groq LLM, Pinecone (RAG)  
- **DevOps:** Docker, Render Cloud  

---

## 📐 System Architecture
```text
Frontend (React + Redux)
          |
          v
   FastAPI (Chatbot & ML)
          |
          v
  Pinecone + Groq LLM (AI Assistant)
          |
          v
   Spring Boot (Core APIs)
          |
   ----------------------
   |                    |
PostgreSQL          SQLite
(Production DB)     (ML/Chatbot DB)
```
```text
git clone https://github.com/adarshiiit0117/agriConnect.git
cd agriConnect
```
```text
# Spring Boot
mvn spring-boot:run

# FastAPI
uvicorn main:app --reload

# React Frontend
npm run dev
#Run with Docker
docker-compose up --build

```

