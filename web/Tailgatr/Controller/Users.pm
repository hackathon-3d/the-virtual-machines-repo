package Tailgatr::Controller::Users;

use strict;
use warnings;
use base 'Mojolicious::Controller';

sub index {
   my $self = shift;

   $self->render(json => 
      Tailgatr::Model::User->select({
         id => $self->param('id')
      })->hashes
   );
}

1;
