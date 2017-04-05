var app = angular.module('docrotasApp', ['ngRoute', 'ui.bootstrap','ngAnimate', 'ngSanitize']);

app.config(['$routeProvider', function($routeProvider){
	$routeProvider.when('/uf',{
		templateUrl: 'views/cadastro_uf.html',
		controller: 'UfCtrl',
		controllerAs: 'ctrl'
	}).when('/cidade',{
        templateUrl: 'views/cadastro_cidade.html',
        controller: 'CidadeCtrl',
        controllerAs: 'ctrl'
    })
}])

app.controller('UfCtrl', ['$http',function ($http, $rootScope, $log, $uibModal) {
    var self = this;
    self.ufs = [];
    self.uf = {};
    self.filtro = {};
    var qtd = 15;
    self.modoGrade = true;
    self.modoFormulario = false;
    self.items = [];

    self.openModalFiltro = function () {
        var paramElem = undefined;
        var modalInstance = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'modalFiltroUf.html',
            controller: 'ModalFiltroUfCtrl',
            controllerAs: 'self',
            size: 'lg',
            appendTo: parentElem,
            resolve: {
                items: function () {
                    return self.items;
                }
            }
        })
    }

    self.tamanhoMax = 15;
    self.totalItens = 1;
    self.paginaAtual = 1;
    self.numPaginas = 1;

    self.pageChanged = function() {
         self.buscarTodos(paginaAtual);
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
        return $http.get('uf?pagina=' + pagina + '&qtd=' + self.tamanhoMax).then(
            function (response) {
                self.ufs = response.data.content;
                self.paginaAtual = response.data.number + 1;
                self.totalItens = response.data.totalElements;
                self.numPaginas = response.data.totalPages;
            }, function (errResponse) {
                console.error('Erro');
            });
    };

    self.buscarPorId = function (id) {
        return $http.get('uf?id=' + id + '&pagina=0&qtd=' + qtd).then(
            function (response) {
                self.uf = response.data.content[0];
            }, function(errReponse) {
                console.error('Erro');
            });
    };

    self.editar = function (id) {
        self.habilitarModoFormulario();
        self.buscarPorId(id);
    }

    self.salvar = function () {
        $http.post('uf/', self.uf)
            .then(function sucesso (response) {
                self.buscarTodos(self.paginaAtual);
                self.novo();
            }, function(response) {
                console.log(response);
            });
    };

    self.novo = function () {
        self.habilitarModoFormulario();
        self.uf = {};
    };

    self.excluir = function (id) {
        $http.delete('uf/' + id)
            .then ( function (response) {
                self.buscarTodos();
                if (id === uf.id) {
                    self.novo();
                }
            }, function (errResponse) {
                console.error(errResponse);
            })
    };
    self.buscarTodos(1);
}]);

app.controller('CidadeCtrl', ['$http', 'ui.grid','ui.grid.pagination', function ($http, $rootScope) {
    var self = this;
    self.cidades = [];
    self.cidade = {};

    self.novo = function () {
        self.cidade = {};
    }

    self.buscarTodos = function () {
        return $http.get('cidade/').then(
            function (response) {
                self.cidades = response.data;
            }, function(errResponse) {
                console.error(errResponse);
            }
        )
    };

    self.buscarPorId = function (id) {
        return $http.get('cidade?id=' + id)
            .then(function (response) {
                self.cidade = response.data[0];
            }, function (errReponse){
                console.error(errResponse);
            });
    };

    self.salvar = function () {
        $http.post('cidade/', self.cidade)
            .then(function (response) {
                self.buscarTodos();
                self.novo();
            })
            .then(function(errResponse){
                console.log(errResponse);
            }); 
    };

    self.excluir = function (id) {
        $http.delete('cidade/' + id)
        .then (function (response) {
            self.buscarTodos();
            if (id === cidade.id) {
                self.novo();
            }
        }, function (errResponse) {
            console.log(errResponse)
        });
    }

    self.buscarTodos();
}]);