angular.module('docrotasApp').controller('CteCtrl', function ($http, $rootScope, $log, $uibModal, $document) {
    var self = this;

    self.modoFormulario = true;
    self.modoGrade = false;
    self.cte = {};
    self.ctes = [];

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
});