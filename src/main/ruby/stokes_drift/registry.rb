require 'java'

module StokesDrift
  class Registry

    def initialize
      @registry = org::stokesdrift::registry::Registry.new
    end

    def put(name, value)
      if value
        config_type = org::stokesdrift::config::RuntimeType::RUBY
        value = org::stokesdrift::registry::RegistryObject.new(config_type, value)
      end
      @registry.put(name, value)
    end

    def get(name)
      if (registry_object = @registry.get(name))
        registry_object.object
      end
    end

    def remove(name)
      @registry.remove(name)
    end

    def self.find
      @@_registry ||= Registry.new
    end

  end


end