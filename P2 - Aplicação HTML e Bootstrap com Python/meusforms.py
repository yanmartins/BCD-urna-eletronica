from flask_wtf import FlaskForm
from wtforms import StringField, PasswordField, SubmitField, IntegerField, FormField, SelectField, FieldList, DateField, \
    DateTimeField
from wtforms.validators import DataRequired

'''
Veja mais na documentação do WTForms

https://wtforms.readthedocs.io/en/stable/
https://wtforms.readthedocs.io/en/stable/fields.html

Um outro pacote interessante para estudar:

https://wtforms-alchemy.readthedocs.io/en/latest/

'''


class LoginForm(FlaskForm):
    username = StringField('Nome de usuário', validators=[DataRequired("O preenchimento desse campo é obrigatório")])
    password = PasswordField('Senha', validators=[DataRequired("O preenchimento desse campo é obrigatório")])
    submit = SubmitField('Entrar')

class EleicaoForm(FlaskForm):
    nome = StringField('Nome da Eleição', validators=[DataRequired("O preenchimento desse campo é obrigatório")])
    submit = SubmitField('Criar')

class QuestaoForm(FlaskForm):
    questao = StringField('Título da questão', validators=[DataRequired("O preenchimento desse campo é obrigatório")])
    numeroDeAlternativas = IntegerField('Número de Alternativas', validators=[DataRequired("O preenchimento desse campo é obrigatório")])
    submit = SubmitField('Criar')

class AlternativaForm(FlaskForm):
    alternativa = StringField('Título da alternativa', validators=[DataRequired("O preenchimento desse campo é obrigatório")])
    submit = SubmitField('Criar')

class EleitorForm(FlaskForm):
    login = StringField('Login', validators=[DataRequired("O preenchimento desse campo é obrigatório")])
    submit = SubmitField('Criar')

class PessoaForm(FlaskForm):
    nome = StringField('Nome de usuário', validators=[DataRequired("O preenchimento desse campo é obrigatório")])
    login = StringField('Login do usuário', validators=[DataRequired("O preenchimento desse campo é obrigatório")])
    senha = PasswordField('Senha', validators=[DataRequired("O preenchimento desse campo é obrigatório")])
    submit = SubmitField('Entrar')