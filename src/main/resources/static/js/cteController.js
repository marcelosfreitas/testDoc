angular.module('docrotasApp').controller('CteCtrl', function ($http, $rootScope, $log, $uibModal, $document) {
    var self = this;

    self.modoFormulario = true;
    self.modoGrade = false;
    self.cte = {};
    self.ctes = [];

    self.tpEmissaoOptions = [
        {id : 0, descricao : 'Normal'},
        {id : 1, descricao : 'EPEC SVC'},
        {id : 2, descricao : 'Contigência FSDA'},
        {id : 3, descricao : 'SVC-RS'},
        {id : 4, descricao : 'SVC-SP'}
    ];

    self.tpAmbienteOptions = [
        {id : 0, descricao : 'Produção'},
        {id : 1, descricao : 'Homologação'}
    ];

    self.tpServicoOptions = [
        {id : 0, descricao : 'Normal'},
        {id : 1, descricao : 'Subcontratação'},
        {id : 2, descricao : 'Resdepacho'},
        {id : 3, descricao : 'Resdepacho Intermediário'},
        {id : 4, descricao : 'Serviço vinculado multimodal'}
    ];

    self.tpCTeOptions = [
        {id : 0, descricao : 'Normal'},
        {id : 1, descricao : 'Complementar'},
        {id : 2, descricao : 'Anulação'},
        {id : 3, descricao : 'Substituto'}
    ];

    self.situacaoOptions = [
        {id : 0, descricao : 'Em edição'},
        {id : 1, descricao : 'Aprovado'},
        {id : 2, descricao : 'Aguardando correção'},
        {id : 3, descricao : 'Falha de validação'},
        {id : 4, descricao : 'Pronto para enviar'},
        {id : 5, descricao : 'Aguardando autorização'},
        {id : 6, descricao : 'Denegado'}
    ]
    self.novo = function () {
        self.habilitarModoFormulario();
        self.cte = {};
    };

    self.atualizar = function () {

    };

    self.salvar = function () {

    };

    self.excluir = function (id) {
        
    };

    self.habilitarModoGrade = function () {
        self.modoGrade = true;
        self.modoFormulario = false;
    };

    self.habilitarModoFormulario = function () {
        self.modoFormulario = true;
        self.modoFormulario = false;
    };

    self.abrirPopUpPesquisaRemetente = function () {
        var modalInstance = $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'popUpPesquisaPessoa.html',
        controller: 'PesquisaPessoaCtrl',
        controllerAs: 'pesquisaPessoaCtrl',
        resolve: {
            items: function () {
                return self.items;
            }
        }
        });

        modalInstance.result.then(function (pessoa) {
            self.cte.pessoaRemetente = pessoa;
        }, function () {
      
        });
    };

    self.abrirPopUpPesquisaDestinatario = function () {
        var modalInstance = $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'popUpPesquisaPessoa.html',
        controller: 'PesquisaPessoaCtrl',
        controllerAs: 'pesquisaPessoaCtrl',
        resolve: {
            items: function () {
                return self.items;
            }
        }
        });

        modalInstance.result.then(function (pessoa) {
            self.cte.pessoaDestinatario = pessoa;
        }, function () {
      
        });
    };

    self.abrirPopUpPesquisaTomador = function () {
        var modalInstance = $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'popUpPesquisaPessoa.html',
        controller: 'PesquisaPessoaCtrl',
        controllerAs: 'pesquisaPessoaCtrl',
        resolve: {
            items: function () {
                return self.items;
            }
        }
        });

        modalInstance.result.then(function (pessoa) {
            self.cte.pessoaTomador = pessoa;
        }, function () {
      
        });
    };

    self.buscarTodos(1);
});

angular.module('docrotasApp').controller('PesquisaPessoaCtrl', function ($uibModalInstance, $http, items) {
    var self = this;
 
    self.campoSelecionado;
    self.valorBusca;
    self.pessoas = [];
    self.tamanhoMax = 5;
    self.totalItens = 1;
    self.paginaAtual = 1;
    self.numPaginas = 1;
    self.pessoaSelecionada;

    self.pageChanged = function() {
         self.buscarTodos(self.paginaAtual);
    };

    self.setPage = function (pageNo) {
       self.paginaAtual = pageNo;
    };

    self.ok = function () {
        $uibModalInstance.close(self.pessoaSelecionada);
    };
    self.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
    self.selecionarPessoa = function (pessoa) {
        self.pessoaSelecionada = pessoa;
        self.ok();
    }

    self.buscarTodos = function (pageNo) {
        var pagina = pageNo - 1;
        var url = 'pessoa?pagina=' + pagina + '&qtd=' + self.tamanhoMax;

        if (self.campoSelecionado && self.valorBusca) {
            if (self.campoSelecionado === "id") {
                 url += '&id=' + self.valorBusca;
            } else if (self.campoSelecionado === "razao") {
                 url += '&razao=' + self.valorBusca;
            } else if (self.campoSelecionado === "fantasia") {
                 url += '&fantasia=' + self.valorBusca;
            } else if (self.campoSelecionado === "cpfCnpj") {
                 url += '&cpfCnpj=' + self.valorBusca;
            }
        }

        return $http.get(url).then(
            function (response) {
                self.pessoas = response.data.content;
                self.paginaAtual = response.data.number + 1;
                self.totalItens = response.data.totalElements;
                self.numPaginas = response.data.totalPages;
            }, function (errResponse) {
                console.error('Erro');
                abrirPopUpErro(errResponse.data.message);
            });
    };

    var abrirPopUpErro = function (msg) {
        var modalInstance = $uibModal.open({
        animation: self.animationsEnabled,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'popUpErro.html',
        controller: 'ErroCtrl',
        controllerAs: 'erroCtrl',
        resolve: {
            msg: function () {
                return msg;
            }
        }
        });
        modalInstance.result.then(function () {
        }, function () {
      
        });
    };
});