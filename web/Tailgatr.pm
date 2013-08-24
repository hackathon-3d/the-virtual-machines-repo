package Tailgatr;

use strict;
use warnings;
use Mojo::Base 'Mojolicious';
use Tailgatr::Model;

sub startup {
   my $self = shift;
   
   $self->mode('development');

   my $r = $self->routes;
   $r->namespaces(['Tailgatr::Controller']);

   $r->route('/')->to(cb => sub {
      my $self = shift;
      $self->render(text => 'This is base URL of the Tailgatr Web API.');
   });

   $r->route('/login')    -> via('post') -> to(controller => 'auths', action => 'create');
   $r->route('/logout')                  -> to(controller => 'auths', action => 'delete');
   $r->route('/register') -> via('post') -> to(controller => 'users', action => 'create');


   my $ru = $r -> bridge('/users') -> to(controller => 'auths', action => 'check');
   
   $ru->route                        -> via('get') -> to(controller => 'users', action => 'getAll');
   $ru->route('/:id', id => qr/\d+/) -> via('get') -> to(controller => 'users', action => 'get');


   Tailgatr::Model->init({
      dsn      => 'dbi:mysql:database=tailgatr;host=localhost;mysql_socket=/run/mysqld/mysqld.sock',
      user     => 'tailgatr',
      password => 'tailgatr'
   });
}

1;
