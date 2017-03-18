var app = angular.module('docrotasApp', ['ngRoute']);

app.config(['$routeProvider', function($routeProvider){
	$routeProvider.when('/uf',{
		templateUrl: 'views/cadastro_uf.html',
		controller: 'UfCtrl',
		controllerAs: 'ctrl'
	})
}])

app.controller('UfCtrl', ['$http',function ($http, $rootScope) {
        var self = this;
        self.ufs = [];
        self.uf = {};
        self.filtro = {};

        self.buscarTodos = function () {
            return $http.get('uf/').then(
                function (response) {
                    self.ufs = response.data;
                }, function (errResponse) {
                    console.error('Erro');
                });
        };

        self.editar = function (id) {
        	return $http.get('uf?id=' + id).then(
        		function (response) {
        			self.uf = response.data[0];
        		}, function(errReponse) {
					console.error('Erro');
				});
        }
        self.salvar = function () {
            $http.post('uf/', self.uf)
                .then(function sucesso (response) {
                	self.buscarTodos();
                	self.novo();
                })
                .then(function(response) {
                    console.log(response);
                });
        };

        self.novo = function () {
            self.uf = {};
        }

        self.excluir = function (id) {
            $http.delete('uf/' + id)
                .then(function () {
                    self.buscarTodos();
                })
                .then(function (response) {
                    self.novo();
                })
        }

        self.buscarTodos();
    }]);