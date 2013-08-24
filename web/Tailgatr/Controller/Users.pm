package Tailgatr::Controller::Users;

use strict;
use warnings;
use base 'Mojolicious::Controller';

sub get {
   my $self = shift;

   $self->render(json => 
      Tailgatr::Model::User->select({
         id => $self->param('id')
      })->hashes
   );
}

sub getAll {
   my $self = shift;

   $self->render(json => 
      Tailgatr::Model::User->select({})->hashes
   );
}

1;
