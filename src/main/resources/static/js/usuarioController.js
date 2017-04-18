angular.module('docrotasApp').controller('UsuarioCtrl', ['$http',function ($http, $rootScope, $log, $uibModal) {
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
        return $http.get('usuario?pagina=' + pagina + '&qtd=' + self.tamanhoMax).then(
            function (response) {
                self.usuarios = response.data.content;
                self.paginaAtual = response.data.number + 1;
                self.totalItens = response.data.totalElements;
                self.numPaginas = response.data.totalPages;
            }, function (errResponse) {
                console.error('Erro');
            });
    };

    self.buscarPorId = function (id) {
        return $http.get('usuario?id=' + id + '&pagina=0&qtd=' + qtd).then(
            function (response) {
                self.usuario = response.data.content[0];
            }, function(errReponse) {
                console.error('Erro');
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
            }, function(response) {
                console.log(response);
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
                console.error(errResponse);
            })
    };
    self.buscarTodos(1);
}]);