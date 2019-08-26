
from flask import Flask, render_template, request, flash, redirect, url_for, session
from werkzeug.security import generate_password_hash, check_password_hash
from flask_bootstrap import Bootstrap
from flask_nav import Nav
from flask_nav.elements import Navbar, View, Subgroup, Link
from flask_sqlalchemy import SQLAlchemy

from meusforms import LoginForm, EleicaoForm, QuestaoForm, AlternativaForm, EleitorForm, PessoaForm

SECRET_KEY = 'aula de BCD - string aleatória'

app = Flask(__name__)
app.secret_key = SECRET_KEY

boostrap = Bootstrap(app) # isso habilita o template bootstrap/base.html

nav = Nav()
nav.init_app(app) # isso habilita a criação de menus de navegação do pacote Flask-Nav

app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///banco_eleicao'
app.config['SQLALCHEMY_TRAK_MODIFICATIONS'] = False

db = SQLAlchemy(app)

class Pessoa(db.Model):
    __tablename__ = 'Pessoa'

    login = db.Column(db.String(40), primary_key=True)
    nome = db.Column(db.String(40))
    senha = db.Column(db.String(40))

    def __init__(self, login, nome, senha):
        self.login = login
        self.nome = nome
        self.senha = generate_password_hash(senha)

    def get_login(self):
        return self.login

    def get_nome(self):
        return self.nome

    def get_senha(self):
        return self.senha

    def validar_senha(self, senha):
        return check_password_hash(self.senha, senha)

class Administrador(db.Model):
    __tablename__ = 'Administrador'
    idAdministrador = db.Column(db.INTEGER, primary_key=True)
    login = db.Column(db.String(40))

    def get_login(self):
        return self.login

class Eleitor(db.Model):
    __tablename__ = 'Eleitor'
    idEleitor = db.Column(db.INTEGER, primary_key=True, autoincrement=True)
    situacaoVoto = db.Column(db.Boolean)
    login = db.Column(db.String(40))
    idEleicao = db.Column(db.INTEGER)

    def __init__(self, situacaoVoto, login, idEleicao):
        self.situacaoVoto = situacaoVoto
        self.login = login
        self.idEleicao = idEleicao

class Eleicao(db.Model):
    __tablename__ = 'Eleicao'
    idEleicao = db.Column(db.INTEGER, primary_key=True, autoincrement=True)
    nome = db.Column(db.String(40))
    iniciada = db.Column(db.BOOLEAN)
    encerrada = db.Column(db.BOOLEAN)
    idAdministrador = db.Column(db.INTEGER)

    def __init__(self, nome, iniciada, encerrada, idAdministrador):
        self.nome = nome
        self.iniciada = iniciada
        self.encerrada = encerrada
        self.idAdministrador = idAdministrador

    def get_nome(self):
        return self.nome

    def get_idEleicao(self):
        return self.idEleicao

class Questoes(db.Model):
    __tablename__ = 'Questoes'
    idQuestao = db.Column(db.INTEGER, primary_key=True, autoincrement=True)
    idEleicao = db.Column(db.INTEGER)
    questao = db.Column(db.String(40))
    numeroDeAlternativas = db.Column(db.INT)

    def __init__(self, idEleicao, questao, numeroDeAlternativas):
        self.idEleicao = idEleicao
        self.questao = questao
        self.numeroDeAlternativas = numeroDeAlternativas

    def get_questao(self):
        return self.questao

    def get_idEleicao(self):
        return self.idEleicao

class Alternativa(db.Model):
    __tablename__ = 'Alternativa'
    idAlternativa = db.Column(db.INTEGER, primary_key=True, autoincrement=True)
    idQuestao = db.Column(db.INT)
    alternativa = db.Column(db.String(40))
    totalDeVotos = db.Column(db.INT)

    def get_alternativa(self):
        return self.alternativa

@nav.navigation()
def meunavbar():
    if not session.get('autenticado'):
        menu = Navbar('Eleições')
        menu.items = [View('Login', 'autenticar')]
    elif session.get('adm'):
        menu = Navbar('Eleições - ADM')
        menu.items = [View('Home', 'inicio'), View('Votar', 'votar'), View('Resultados', 'resultados')]
        menu.items.append(Subgroup('Cadastrar', View('Eleitores', 'inserir'), View('Pessoas', 'inserir_pessoa')))
        menu.items.append(Subgroup('Criar', View('Eleição', 'criar_eleicao'), View('Questão', 'set_eleicao_questao'), View('Alternativa', 'set_q_a')))
        menu.items.append(Subgroup('Regular estado', View('Abrir eleição', 'abrir'), View('Encerrar eleição', 'encerrar')))
        menu.items.append(View('Apuração', 'apuracao'))
        menu.items.append(Link('Logout', 'logout'))
    elif session.get('autenticado') and not session.get('adm'):
        menu = Navbar('Eleições - Eleitor')
        menu.items = [View('Home', 'inicio'), View('Votar', 'votar'), View('Resultados', 'resultados')]
        menu.items.append(Link('Logout', 'logout'))
    return menu

@app.route('/inserir_pessoa', methods=['GET', 'POST'])
def inserir_pessoa():
    if not session.get('adm'):
        return redirect(url_for('autenticar'))
    else:
        form = PessoaForm()

        if form.validate_on_submit():
            login = form.login.data
            nome = form.nome.data
            senha = form.senha.data

            if Pessoa.query.filter_by(login=login).first() is None:
                nova = Pessoa(login=login, nome=nome, senha=senha)
                db.session.add(nova)
                db.session.commit()
                return redirect(url_for('inicio'))
            else:
                return render_template('erro_registro.html')

        return render_template('registrar_pessoa.html', form=form)


@app.route('/inserir_eleitor', methods=['GET', 'POST'])
def inserir():
    if not session.get('adm'):
        return redirect(url_for('autenticar'))
    else:
        administrador = Administrador.query.filter_by(login=session.get('username')).first()
        eleicoes = Eleicao.query.filter_by(idAdministrador=administrador.idAdministrador).all()

        el = []
        for e in eleicoes:
            if not e.encerrada:
                el.append(Eleicao.query.filter_by(idEleicao=e.idEleicao).first())

        if (len(el) == 0):
            return render_template('abertura_negada.html')
        return render_template('selec_eleicao_eleitor.html', lista=el)

@app.route('/registrar_eleitor', methods=['GET', 'POST'])
def registrar():
    if not session.get('adm'):
        return redirect(url_for('autenticar'))

    idE = str(request.args.get('id'))
    form = EleitorForm()

    if form.validate_on_submit():
        login_eleitor = form.login.data
        if not Pessoa.query.filter_by(login=login_eleitor).first() is None:
            nova = Eleitor(login=login_eleitor, situacaoVoto = False, idEleicao=idE)
            db.session.add(nova)
            db.session.commit()
            return redirect(url_for('inicio'))
        else:
            return render_template('erro_registro.html')

    return render_template('registrar_eleitor.html', form=form)

@app.route('/login', methods=['GET', 'POST'])
def autenticar():
    form = LoginForm()

    if form.validate_on_submit():

        user = form.username.data
        senha = form.password.data

        p = Pessoa.query.filter_by(login=user).first()
        adm = Administrador.query.filter_by(login=user).first()

        if p is not None:
            if p.validar_senha(senha):
                session['username'] = user
                session['autenticado'] = True
                g = p.get_nome()
                if adm is not None:
                    if adm.get_login() == user:
                        session['adm'] = True
                return render_template('autenticado.html', title="Usuário autenticado", name=g)
    return render_template('login.html', title='Autenticação de usuários', form=form)

@app.route('/')
def inicio():

    if not session.get('autenticado'):
        return render_template('index.html')

    else:
        p = Pessoa.query.filter_by(login=session.get('username')).first()
        g = p.get_nome()
        return render_template('autenticado.html', title="Usuário autenticado", name=g)

@app.route('/logout')
def logout():
    session['autenticado'] = False
    session['adm'] = False
    return redirect(url_for('autenticar'))

@app.route('/abrir', methods=['POST', 'GET'])
def abrir():

    if not session.get('adm'):
        return redirect(url_for('autenticar'))

    else:
        if request.method == 'GET':

            administrador = Administrador.query.filter_by(login=session.get('username')).first()
            eleicoes = Eleicao.query.filter_by(idAdministrador=administrador.idAdministrador).all()

            el = []
            for e in eleicoes:
                if not e.iniciada:
                    el.append(Eleicao.query.filter_by(idEleicao=e.idEleicao).first())

            if (len(el) == 0):
                 return render_template('abertura_negada.html')

            return render_template('abrir.html', lista=el)
        else:
            eleicao_aberta = []

            for campo in request.form.items():
                if 'alt-' in campo[0]:
                    eleicao_aberta.append(campo[0].split('-')[1])

            for id in eleicao_aberta:
                eleicao = Eleicao.query.filter_by(idEleicao=id).first()
                eleicao.iniciada = True
                db.session.commit()

            return redirect(url_for('abrir'))

@app.route('/criar_eleicao', methods=['GET', 'POST'])
def criar_eleicao():
    if not session.get('adm'):
        return redirect(url_for('autenticar'))

    form = EleicaoForm()
    if form.validate_on_submit():
        adm = Administrador.query.filter_by(login=session.get('username')).first()
        idADM = adm.idAdministrador
        nome_eleicao = form.nome.data
        nova = Eleicao(nome=nome_eleicao, iniciada=False, encerrada=False, idAdministrador=idADM)
        db.session.add(nova)
        db.session.commit()
        return redirect(url_for('inicio'))
    return render_template('criar_eleicao.html', form=form)

@app.route('/selecionar_eleicao_questao', methods=['POST', 'GET'])
def set_eleicao_questao():
    if not session.get('adm'):
        return redirect(url_for('autenticar'))
    else:
        administrador = Administrador.query.filter_by(login=session.get('username')).first()
        eleicoes = Eleicao.query.filter_by(idAdministrador=administrador.idAdministrador).all()

        el = []
        for e in eleicoes:
            if not e.encerrada:
                el.append(Eleicao.query.filter_by(idEleicao=e.idEleicao).first())

        if (len(el) == 0):
            return render_template('abertura_negada.html')
        return render_template('selec_eleicao_q.html', lista=el)

@app.route('/criar_questao', methods=['POST', 'GET'])
def criar_questao():
    if not session.get('adm'):
        return redirect(url_for('autenticar'))

    idEleicao = str(request.args.get('id'))

    form = QuestaoForm()
    if form.validate_on_submit():
        nome_questao = form.questao.data
        numero = form.numeroDeAlternativas.data
        nova = Questoes(idEleicao=idEleicao, questao=nome_questao, numeroDeAlternativas=numero)

        db.session.add(nova)
        db.session.commit()

        qNova = Questoes.query.filter_by(questao=nome_questao).first()
        aNula = Alternativa(idQuestao=qNova.idQuestao, alternativa="Branco", totalDeVotos=0)
        aBranco = Alternativa(idQuestao=qNova.idQuestao, alternativa="Nulo", totalDeVotos=0)
        db.session.add(aNula)
        db.session.add(aBranco)
        db.session.commit()
        return redirect(url_for('inicio'))
    return render_template('criar_questao.html', form=form)

@app.route('/selecionar_questao_alternativa', methods=['POST', 'GET'])
def set_q_a():
    if not session.get('adm'):
        return redirect(url_for('autenticar'))
    else:
        administrador = Administrador.query.filter_by(login=session.get('username')).first()
        eleicoes = Eleicao.query.filter_by(idAdministrador=administrador.idAdministrador).all()
        questoes = Questoes.query

        el = []
        for e in eleicoes:
            if not e.encerrada:
                el.append(Eleicao.query.filter_by(idEleicao=e.idEleicao).first())

        if (len(el) == 0):
            return render_template('abertura_negada.html')

        return render_template('selec_q_a.html', questao=questoes, eleicao=el)

@app.route('/criar_alternativa', methods=['POST', 'GET'])
def criar_alternativa():
    if not session.get('adm'):
        return redirect(url_for('autenticar'))

    idQ = str(request.args.get('id'))

    form = AlternativaForm()

    if form.validate_on_submit():
        nome_alternativa = form.alternativa.data
        nova = Alternativa(idQuestao=idQ, alternativa=nome_alternativa, totalDeVotos=0)
        db.session.add(nova)
        db.session.commit()
        return redirect(url_for('inicio'))
    return render_template('criar_alternativa.html', form=form)

@app.route('/encerrar', methods=['POST', 'GET'])
def encerrar():
    if not session.get('adm'):
        return redirect(url_for('autenticar'))
    if request.method == 'GET':

        administrador = Administrador.query.filter_by(login=session.get('username')).first()
        eleicoes = Eleicao.query.filter_by(idAdministrador=administrador.idAdministrador).all()

        el = []
        for e in eleicoes:
            if e.iniciada and not e.encerrada:
                el.append(Eleicao.query.filter_by(idEleicao=e.idEleicao).first())

        if (len(el) == 0):
            return render_template('encerramento_negado.html')

        return render_template('encerrar.html', lista=el)
    else:
        eleicao_encerrada = []

        for campo in request.form.items():
            if 'alt-' in campo[0]:
                eleicao_encerrada.append(campo[0].split('-')[1])

        for id in eleicao_encerrada:
            eleicao = Eleicao.query.filter_by(idEleicao=id).first()
            eleicao.encerrada = True
            db.session.commit()

        return redirect(url_for('encerrar'))

@app.route('/apuracao', methods=['POST', 'GET'])
def apuracao():
    if not session.get('autenticado'):
        return redirect(url_for('autenticar'))
    else:
        if request.method == 'GET':

            eleitor = Eleitor.query.filter_by(login=session.get('username')).all()
            eleicoes = []
            for e in eleitor:
                if Eleicao.query.filter_by(idEleicao=e.idEleicao).first().encerrada:
                    eleicoes.append(Eleicao.query.filter_by(idEleicao=e.idEleicao).first())

            if (len(eleicoes) == 0):
                return render_template('resultado_negado.html')

            return render_template('apuracao.html', lista=eleicoes)

        else:
            eleicao_aberta = []

            for campo in request.form.items():
                if 'alt-' in campo[0]:
                    eleicao_aberta.append(campo[0].split('-')[1])

            for id in eleicao_aberta:
                eleicao = Eleicao.query.filter_by(idEleicao=id).first()
                eleicao.iniciada = True
                db.session.commit()

            alternativas = Alternativa.query
            return render_template('apuracao.html', lista=alternativas)

@app.route('/ver_apuracao')
def ver_apuracao():
    if not session.get('autenticado'):
        return redirect(url_for('autenticar'))

    else:
        idEleicao = str(request.args.get('id'))
        eleicao = Eleicao.query.filter_by(idEleicao=idEleicao).first()
        questoes = Questoes.query.filter_by(idEleicao=idEleicao).all()
        alternativas = Alternativa.query
        return render_template('ver_apuracao.html', questao=questoes, alternativa=alternativas, eleicao=eleicao.nome)

@app.route('/resultados', methods=['POST', 'GET'])
def resultados():
    if not session.get('autenticado'):
        return redirect(url_for('autenticar'))
    else:
        if request.method == 'GET':

            eleitor = Eleitor.query.filter_by(login=session.get('username')).all()
            eleicoes = []
            for e in eleitor:
                if Eleicao.query.filter_by(idEleicao=e.idEleicao).first().encerrada:
                    eleicoes.append(Eleicao.query.filter_by(idEleicao=e.idEleicao).first())

            if (len(eleicoes) == 0):
                 return render_template('resultado_negado.html')

            return render_template('resultado.html', lista=eleicoes)

        else:
            eleicao_aberta = []

            for campo in request.form.items():
                if 'alt-' in campo[0]:
                    eleicao_aberta.append(campo[0].split('-')[1])

            for id in eleicao_aberta:
                eleicao = Eleicao.query.filter_by(idEleicao=id).first()
                eleicao.iniciada = True
                db.session.commit()

            alternativas = Alternativa.query
            return render_template('resultado.html', lista=alternativas)

@app.route('/ver_resultado')
def ver_resultado():
    if not session.get('autenticado'):
        return redirect(url_for('autenticar'))

    else:
        idEleicao = str(request.args.get('id'))
        eleicao = Eleicao.query.filter_by(idEleicao=idEleicao).first()
        questoes = Questoes.query.filter_by(idEleicao=idEleicao).all()
        alternativas = Alternativa.query
        return render_template('ver_resultado.html', questao=questoes, alternativa=alternativas, eleicao=eleicao.nome)

@app.route('/votar')
def votar():
    if not session.get('autenticado'):
        return redirect(url_for('autenticar'))
    else:
        eleitor = Eleitor.query.filter_by(login=session.get('username')).all()
        eleicoes = []
        for e in eleitor:
            if not e.situacaoVoto:
                if Eleicao.query.filter_by(idEleicao=e.idEleicao).first().iniciada and not Eleicao.query.filter_by(idEleicao=e.idEleicao).first().encerrada:
                    eleicoes.append(Eleicao.query.filter_by(idEleicao=e.idEleicao).first())

        if(len(eleicoes) == 0):
            return render_template('votacao_negada.html')
        return render_template('votar.html', lista=eleicoes)

@app.route('/cabine_de_votacao',  methods=['POST', 'GET'])
def cabine():
    global idEleicao

    if not session.get('autenticado'):
        return redirect(url_for('autenticar'))

    else:
        if request.method == 'GET':
            idEleicao = str(request.args.get('id'))
            eleicao = Eleicao.query.filter_by(idEleicao=idEleicao).first()
            if eleicao.encerrada:
                return render_template('votacao_negada2.html')
            questoes = Questoes.query.filter_by(idEleicao=idEleicao).all()
            alternativas = Alternativa.query
            return render_template('cabine_de_votacao.html', lista=questoes, alternativa=alternativas, eleicao=eleicao.nome)
        else:

            usuario = Eleitor.query.filter_by(login=session.get('username')).all()

            for i in usuario:
                if i.idEleicao == int(idEleicao):
                    if i.situacaoVoto == True:
                        return render_template('votacao_negada2.html')
                    i.situacaoVoto = True

            db.session.commit()

            voto = []
            for campo in request.form.items():
                if 'alt-' in campo[0]:
                    voto.append(campo[0].split('-')[1])
            for id in voto:

                alternativa = Alternativa.query.filter_by(idAlternativa=id).first()
                alternativa.totalDeVotos = (alternativa.totalDeVotos + 1)
                db.session.commit()

            return redirect(url_for('votar'))

@app.errorhandler(404)
def page_not_found(e):
    '''
    Para tratar erros de páginas não encontradas - HTTP 404
    :param e:
    :return:
    '''
    return render_template('404.html'), 404


if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)