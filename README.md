# App de Biblioteca (AP2)

Este repositório contém o projeto completo de um aplicativo de Biblioteca para a Avaliação Parcial 2 (AP2), seguindo os padrões do Clean Code e arquitetado para ter um frontend Android e um backend com API REST.

## Tecnologias Utilizadas
- **Android Frontend:** Kotlin, Retrofit, Glide, Material Design, XML Layouts.
- **Backend API:** Python, FastAPI, SQLAlchemy, Uvicorn.
- **Banco de Dados:** SQLite (Relacional e local).

## Funcionalidades Atendidas (Requisitos)
- Mais de 5 telas (LoginActivity, MainActivity, BookDetailActivity, BorrowActivity, HomeFragment, ProfileFragment).
- Fragmentos e Activities com Intents explícitas e implícitas (Compartilhar).
- Mais de 6 componentes de UI (TextView, EditText, Button, ImageView, CheckBox, Spinner, Switch, RecyclerView, ProgressBar, CardView).
- Consumo de API REST documentada (Swagger em `/docs`).
- Persistência em banco de dados relacional (SQLite).
- Código totalmente sem comentários e aplicando princípios Clean Code.

## Como Executar o Backend (VS Code)
1. Abra a pasta `backend` no VS Code ou no seu terminal de preferência.
2. Certifique-se de ter o Python instalado.
3. Instale as dependências:
   ```bash
   pip install -r requirements.txt
   ```
4. Execute o servidor:
   ```bash
   uvicorn main:app --reload --host 0.0.0.0 --port 8000
   ```
5. A API estará rodando. A documentação Swagger pode ser acessada em `http://localhost:8000/docs`.

**Popule o Banco (Opcional):**
Abra o Swagger e faça um `POST` em `/books` com alguns livros para que eles apareçam no app. Exemplo:
```json
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "description": "Boas práticas de código",
  "image_url": "https://m.media-amazon.com/images/I/41xShlnTZTL._SX376_BO1,204,203,200_.jpg",
  "available": true
}
```

## Como Executar o Aplicativo Android (Android Studio)
1. Abra o **Android Studio**.
2. Vá em `File > Open...` e selecione a pasta `android` (`C:\Users\Olá\.gemini\antigravity\scratch\LibraryApp\android`).
3. Aguarde o Gradle sincronizar (se houver erros, clique no botão do elefante "Sync Project with Gradle Files").
4. Clique em **Run (Shift + F10)** e selecione um Emulador (AVD).
5. O aplicativo irá abrir. O IP da API já está configurado como `10.0.2.2:8000` no `RetrofitClient`, que é a forma como o emulador acessa o `localhost` da sua máquina.

Aproveite seu projeto!
