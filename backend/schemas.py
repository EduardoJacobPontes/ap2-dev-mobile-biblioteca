from pydantic import BaseModel
from typing import Optional

class LivroBase(BaseModel):
    titulo: str
    autor: str
    descricao: str
    imagem_url: str
    disponivel: bool = True

class LivroCreate(LivroBase):
    pass

class Livro(LivroBase):
    id: int

    class Config:
        orm_mode = True

class EmprestimoBase(BaseModel):
    livro_id: int
    usuario_id: str
    data_emprestimo: str
    data_devolucao: str

class EmprestimoCreate(EmprestimoBase):
    pass

class Emprestimo(EmprestimoBase):
    id: int
    devolvido: bool
    livro: Optional[Livro] = None

    class Config:
        orm_mode = True
