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

   my $ra = $r->bridge('/users')->to(controller => 'auth', action => 'check');
   $ra->route('/:id', id => qr/\d+/)->via('get')->to(controller => 'users', action => 'index');

   Tailgatr::Model->init({
      dsn      => 'dbi:mysql:database=tailgatr;host=localhost;mysql_socket=/run/mysqld/mysqld.sock',
      user     => 'tailgatr',
      password => 'tailgatr'
   });
}

1;
