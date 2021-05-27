# coding: utf-8
Gem::Specification.new do |spec|
  spec.name          = "stokesdrift"
  spec.version       = "0.2.4"
  spec.authors       = ["Daniel Marchant"]
  spec.email         = ["dan@driedtoast.com"]
  spec.summary       = %q{Stokes Drift}
  spec.description   = "Provides a micro service container for the modern world"
  spec.homepage      = "https://github.com/stokesdrift/stokesdrift"
  spec.license       = "MIT"

  spec.files += Dir['bin/**', 'lib/**/*', 'scripts/**']
  spec.bindir = 'bin'
  spec.executables << 'stokesdrift'

  spec.add_dependency 'rack', '~> 2.1', '>= 2.1.4'
  
  spec.add_development_dependency 'bundler', '~> 2.2.17'
  spec.add_development_dependency 'rake', '~> 11.2.2'
  spec.add_development_dependency 'rspec', '~> 3.10.0'

end
