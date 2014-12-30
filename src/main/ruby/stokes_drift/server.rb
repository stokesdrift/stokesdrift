require 'java'

module StokesDrift
  class Server

    def initialize(args)
       @server = org::stokesdrift::Server.new(args)
    end

    def start
      @server.start
    end

    def stop
      @server.stop
    end

  end
end
