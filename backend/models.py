from sqlalchemy import Boolean, Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from database import Base

class Livro(Base):
    __tablename__ = "livros"

    id = Column(Integer, primary_key=True, index=True)
    titulo = Column(String, index=True)
    autor = Column(String)
    descricao = Column(String)
    imagem_url = Column(String)
    disponivel = Column(Boolean, default=True)

class Emprestimo(Base):
    __tablename__ = "emprestimos"

    id = Column(Integer, primary_key=True, index=True)
    livro_id = Column(Integer, ForeignKey("livros.id"))
    usuario_id = Column(String, index=True)
    data_emprestimo = Column(String)
    data_devolucao = Column(String)
    devolvido = Column(Boolean, default=False)

    livro = relationship("Livro")
