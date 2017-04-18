angular.module('docrotasApp').controller('UfCtrl', ['$http',function ($http, $rootScope, $log, $uibModal) {
    var self = this;
    self.ufs = [];
    self.uf = {};
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