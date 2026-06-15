from fastapi import FastAPI, Depends, HTTPException
from sqlalchemy.orm import Session
from typing import List
import models, schemas
from database import SessionLocal, engine

models.Base.metadata.create_all(bind=engine)

app = FastAPI(title="API do Aplicativo de Biblioteca")

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@app.get("/livros", response_model=List[schemas.Livro], summary="Listar Livros")
def listar_livros(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    return db.query(models.Livro).offset(skip).limit(limit).all()

@app.get("/livros/{livro_id}", response_model=schemas.Livro, summary="Detalhar Livro")
def detalhar_livro(livro_id: int, db: Session = Depends(get_db)):
    livro = db.query(models.Livro).filter(models.Livro.id == livro_id).first()
    if livro is None:
        raise HTTPException(status_code=404, detail="Livro não encontrado")
    return livro

@app.post("/livros", response_model=schemas.Livro, summary="Cadastrar Livro")
def cadastrar_livro(livro: schemas.LivroCreate, db: Session = Depends(get_db)):
    db_livro = models.Livro(**livro.dict())
    db.add(db_livro)
    db.commit()
    db.refresh(db_livro)
    return db_livro

@app.post("/emprestimos", response_model=schemas.Emprestimo, summary="Realizar Empréstimo")
def realizar_emprestimo(emprestimo: schemas.EmprestimoCreate, db: Session = Depends(get_db)):
    livro = db.query(models.Livro).filter(models.Livro.id == emprestimo.livro_id).first()
    if not livro or not livro.disponivel:
        raise HTTPException(status_code=400, detail="Livro não está disponível")
    
    livro.disponivel = False
    db_emprestimo = models.Emprestimo(**emprestimo.dict())
    db.add(db_emprestimo)
    db.commit()
    db.refresh(db_emprestimo)
    return db_emprestimo

@app.get("/emprestimos/{usuario_id}", response_model=List[schemas.Emprestimo], summary="Listar Empréstimos do Usuário")
def listar_emprestimos_usuario(usuario_id: str, db: Session = Depends(get_db)):
    return db.query(models.Emprestimo).filter(models.Emprestimo.usuario_id == usuario_id).all()

@app.put("/emprestimos/{emprestimo_id}/devolver", response_model=schemas.Emprestimo, summary="Devolver Livro")
def devolver_livro(emprestimo_id: int, db: Session = Depends(get_db)):
    emprestimo = db.query(models.Emprestimo).filter(models.Emprestimo.id == emprestimo_id).first()
    if not emprestimo:
        raise HTTPException(status_code=404, detail="Empréstimo não encontrado")
    if emprestimo.devolvido:
        raise HTTPException(status_code=400, detail="Livro já devolvido")
    
    emprestimo.devolvido = True
    livro = db.query(models.Livro).filter(models.Livro.id == emprestimo.livro_id).first()
    if livro:
        livro.disponivel = True
    
    db.commit()
    db.refresh(emprestimo)
    return emprestimo
