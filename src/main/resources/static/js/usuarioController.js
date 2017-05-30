angular.module('docrotasApp').controller('UsuarioCtrl', function ($http, $rootScope, $log, $uibModal, $document) {
    var self = this;
    self.usuarios = [];
    self.usuario = {};
    self.filtro = {};
    var qtd = 15;
    self.modoGrade = true;
    self.modoFormulario = false;
    self.items = [];

    self.tamanhoMax = 15;
    self.totalItens = 1;
    self.paginaAtual = 1;
    self.numPaginas = 1;

    self.pageChanged = function() {
         self.buscarTodos(self.paginaAtual);
    };

    self.setPage = function (pageNo) {
       self.paginaAtual = pageNo;
    };

    self.habilitarModoGrade = function () {
        self.modoGrade = true;
        self.modoFormulario = false;
    }

    self.habilitarModoFormulario = function () {
        self.modoGrade = false;
        self.modoFormulario = true;
    }

    self.buscarTodos = function (pageNo) {
        var pagina = pageNo - 1;
        var url = 'usuario?pagina=' + pagina + '&qtd=' + self.tamanhoMax;

        if (self.campoSelecionado && self.valorBusca) {
            if (self.campoSelecionado === "id") {
                url += '&id=' + self.valorBusca;
            } else if (self.campoSelecionado === "login") {
                url += '&login=' + self.valorBusca;
            } else if (self.campoSelecionado === "email") {
                url += '&email=' + self.valorBusca;
            }
        }

        return $http.get(url).then(
            function (response) {
                self.usuarios = response.data.content;
                self.paginaAtual = response.data.number + 1;
                self.totalItens = response.data.totalElements;
                self.numPaginas = response.data.totalPages;
            }, function (errResponse) {
                abrirPopUpErro(errResponse.data.message);
            });
    };

    self.buscarPorId = function (id) {
        return $http.get('usuario?id=' + id + '&pagina=0&qtd=' + qtd).then(
            function (response) {
                self.usuario = response.data.content[0];
            }, function(errReponse) {
                abrirPopUpErro(errResponse.data.message);
            });
    };

    self.editar = function (id) {
        self.habilitarModoFormulario();
        self.buscarPorId(id);
    }

    self.salvar = function () {
        $http.post('usuario/', self.usuario)
            .then(function sucesso (response) {
                self.buscarTodos(self.paginaAtual);
                self.novo();
            }, function(errResponse) {
                abrirPopUpErro(errResponse.data.message);
            });
    };

    self.novo = function () {
        self.habilitarModoFormulario();
        self.usuario = {};
    };

    self.excluir = function (id) {
        $http.delete('usuario/' + id)
            .then ( function (response) {
                self.buscarTodos();
                if (id === usuario.id) {
                    self.novo();
                }
            }, function (errResponse) {
                 abrirPopUpErro(errResponse.data.message);
            })
    };
    self.buscarTodos(1);

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

angular.module('docrotasApp').controller('ErroCtrl', function ($uibModalInstance, $http, msg) {
    var self = this;
    
    self.mensagem = msg;

    self.ok = function () {
        $uibModalInstance.close();
    };
});