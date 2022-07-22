
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Properties;
import java.util.Scanner;

public class Utils {

	static Scanner teclado = new Scanner(System.in);

	public static Connection conectar() {
		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "1234");
		props.setProperty("ssl", "false");
		String URL_SERVIDOR = "jdbc:postgresql://localhost:5432/clinicavet";

		try {
			return DriverManager.getConnection(URL_SERVIDOR, props);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof ClassNotFoundException) {
				System.err.println("Verifique o driver de conexão.");
			} else {
				System.err.println("Verifique se o servidor está ativo.");
			}
			System.exit(-42);
			return null;
		}
	}

	public static void desconectar(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void listarveterinario() {
		String BUSCAR_TODOS = "Select * from veterinario;";

		try {
			Connection conn = conectar();
			PreparedStatement veterinario = conn.prepareStatement(BUSCAR_TODOS, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet res = veterinario.executeQuery();

			res.last();
			int qtd = res.getRow();
			res.beforeFirst();

			if (qtd > 0) {
				System.out.println("Listando veterinarios...");
				System.out.println("-----------------------------------");
				while (res.next()) {
					System.out.println("Registro: " + res.getInt(1));
					System.out.println("CPF: " + res.getString(2));
					System.out.println("Nome: " + res.getString(3));
					System.out.println("Data de nascimento: " + res.getDate(4));
					System.out.println("Logradouro: " + res.getString(5));
					System.out.println("-----------------------------------");
				}
			} else {
				System.out.println("Nao existe veterinario.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro buscando todos veterinarios.");
			System.exit(-42);
		}
	}
	
	public static void listartutor() {
		String BUSCAR_TODOS = "Select * from tutor;";

		try {
			Connection conn = conectar();
			PreparedStatement tutor = conn.prepareStatement(BUSCAR_TODOS, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet res = tutor.executeQuery();

			res.last();
			int qtd = res.getRow();
			res.beforeFirst();

			if (qtd > 0) {
				System.out.println("Listando tutor...");
				System.out.println("-----------------------------------");
				while (res.next()) {
					System.out.println("CPF: " + res.getString(1));
					System.out.println("Nome: " + res.getString(2));
					System.out.println("Data de nascimento: " + res.getDate(3));
					System.out.println("Logradouro: " + res.getString(4));
					System.out.println("-----------------------------------");
				}
			} else {
				System.out.println("Nao existe tutor.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro buscando todos tutores.");
			System.exit(-42);
		}
	}
	
	public static void listaranimal() {
		String BUSCAR_TODOS = "Select * from animal;";

		try {
			Connection conn = conectar();
			PreparedStatement animal = conn.prepareStatement(BUSCAR_TODOS, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet res = animal.executeQuery();

			res.last();
			int qtd = res.getRow();
			res.beforeFirst();

			if (qtd > 0) {
				System.out.println("Listando animais...");
				System.out.println("-----------------------------------");
				while (res.next()) {
					System.out.println("CPF do Tutor: " + res.getString(1));
					System.out.println("ID: " + res.getInt(2));
					System.out.println("Nome: " + res.getString(3));
					System.out.println("Raca: " + res.getString(4));
					System.out.println("Data de nascimento: " + res.getDate(5));
					System.out.println("-----------------------------------");
				}
			} else {
				System.out.println("Nao existe animal.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro buscando todos animais.");
			System.exit(-42);
		}
	}

	public static void listaragenda() {
		String BUSCAR_TODOS = "Select agendamento.*, veterinario.nome, animal.nomeAnimal from agendamento "
				+ "left join veterinario ON agendamento.veterinario = veterinario.registro "
				+ "left join animal ON agendamento.animal = animal.codigo";

		try {
			Connection conn = conectar();
			PreparedStatement agendamento = conn.prepareStatement(BUSCAR_TODOS, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet res = agendamento.executeQuery();

			res.last();
			int qtd = res.getRow();
			res.beforeFirst();

			if (qtd > 0) {
				System.out.println("Listando agendamentos...");
				System.out.println("-----------------------------------");
				while (res.next()) {
					System.out.println("ID: " + res.getInt(1));
					System.out.println("Data: " + res.getDate(2));
					System.out.println("Hora: " + res.getTime(3));
					System.out.println("Veterinario: " + res.getInt(4));
					System.out.println("Nome: " + res.getString(6));
					System.out.println("Animal: " + res.getInt(5));
					System.out.println("Nome: " + res.getString(7));
					System.out.println("-----------------------------------");
				}
			} else {
				System.out.println("Nao existe agendamento.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro buscando todos agendamentos.");
			System.exit(-42);
		}
	}
	
	public static void inserirveterinario() {
		System.out.print("Informe o registro: ");
		int registro = teclado.nextInt();

		System.out.print("\nInforme o CPF: ");
		String cpf = teclado.next();

		System.out.print("\nInforme o nome: ");
		String nome = teclado.next();

		System.out.print("\nInforme a data de nascimento: ");
		String datadenasc = teclado.next();
		java.sql.Date dtValue = java.sql.Date.valueOf(datadenasc);

		System.out.print("Informe o logradouro: ");
		String logradouro = teclado.next();

		String INSERIR = "insert into veterinario(registro,cpf,nome,datadenasc,logradouro) " + "values (?, ?, ?, ?, ?)";

		try {
			Connection conn = conectar();
			PreparedStatement salvar = conn.prepareStatement(INSERIR);

			salvar.setInt(1, registro);
			salvar.setString(2, cpf);
			salvar.setString(3, nome);
			salvar.setDate(4, dtValue);
			salvar.setString(5, logradouro);

			salvar.executeUpdate();
			salvar.close();
			desconectar(conn);
			System.out.println("O veterinario " + nome + " foi inserido com sucesso!");

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro ao inserir veterinario");
		}

	}

	public static void inserirtutor() {

		System.out.print("\nInforme o CPF: ");
		String cpf = teclado.next();

		System.out.print("\nInforme o nome: ");
		String nome = teclado.next();

		System.out.print("\nInforme a data de nascimento: ");
		String datadenasc = teclado.next();
		java.sql.Date dtValue = java.sql.Date.valueOf(datadenasc);

		System.out.print("\nInforme o logradouro: ");
		String logradouro= teclado.next();

		String INSERIR = "insert into tutor(cpf,nome,datadenasc,logradouro) values (?, ?, ?, ?)";

		try {
			Connection conn = conectar();
			PreparedStatement salvar = conn.prepareStatement(INSERIR);

			salvar.setString(1, cpf);
			salvar.setString(2, nome);
			salvar.setDate(3, dtValue);
			salvar.setString(4, logradouro);

			salvar.executeUpdate();
			salvar.close();
			desconectar(conn);
			System.out.println("O tutor " + nome + " foi inserido com sucesso!");

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro ao inserir tutor");
		}

	}

	public static void inseriranimal() {
		System.out.print("\nInforme o CPF do Tutor: ");
		String cpfTutor = teclado.next();

		System.out.print("\nInforme o nome: ");
		String nomeAnimal = teclado.next();

		System.out.print("\nInforme a raca: ");
		String raca = teclado.next();

		System.out.print("\nInforme a data de nascimento: ");
		String datadenasc = teclado.next();
		java.sql.Date dtValue = java.sql.Date.valueOf(datadenasc);

		String INSERIR = "insert into animal(cpftutor,nomeanimal,raca,datadenasc) values (?, ?, ?, ?)";

		try {
			Connection conn = conectar();
			PreparedStatement salvar = conn.prepareStatement(INSERIR);

			salvar.setString(1, cpfTutor);
			salvar.setString(2, nomeAnimal);
			salvar.setString(3, raca);
			salvar.setDate(4, dtValue);

			salvar.executeUpdate();
			salvar.close();
			desconectar(conn);
			System.out.println("O animal " + nomeAnimal + " foi inserido com sucesso!");

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro ao inserir animal");
		}

	}

	public static void inserirconsulta() {

		System.out.print("\nInforme a data do agendamento: ");
		String dataagendamento = teclado.next();
		java.sql.Date dtValue = java.sql.Date.valueOf(dataagendamento);
		
		
		System.out.print("\nInforme a hora do agendamento: ");
		String horaagendamento = teclado.next();
		java.sql.Time dtValue1 = java.sql.Time.valueOf(horaagendamento);
		
		System.out.print("\nInforme o ID do veterinario: ");
		int idvet = teclado.nextInt();

		System.out.print("\nInforme o ID do animal: ");
		int idanimal = teclado.nextInt();

		String INSERIR = "insert into agendamento(dataagendamento,horaagendamento,veterinario,animal) values (?, ?, ?, ?)";

		try {
			Connection conn = conectar();
			PreparedStatement salvar = conn.prepareStatement(INSERIR);

			salvar.setDate(1, dtValue);
			salvar.setTime(2, (Time) dtValue1);
			salvar.setInt(3, idvet);
			salvar.setInt(4, idanimal);

			salvar.executeUpdate();
			salvar.close();
			desconectar(conn);
			System.out.println("A consulta foi agendada para o dia " + dtValue + " as " + horaagendamento + " ");

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro ao inserir agendamento");
		}
	}

	public static void atualizarveterinario() {
		System.out.print("Informe o registro do veterinario: ");
		int registro = teclado.nextInt();
		
		String BUSCAR_POR_PK = "select * from veterinario where registro=?";
		
		try {
			Connection conn = conectar();
			PreparedStatement veterinario = conn.prepareStatement(
					BUSCAR_POR_PK,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			veterinario.setInt(1, registro);
			ResultSet res = veterinario.executeQuery();

			res.last();
			int qtd = res.getRow();
			res.beforeFirst();
			
			if(qtd > 0) {
				System.out.print("\nInforme o CPF: ");
				String cpf = teclado.next();

				System.out.print("\nInforme o nome: ");
				String nome = teclado.next();

				System.out.print("\nInforme a data de nascimento: ");
				String datadenasc = teclado.next();
				java.sql.Date dtValue = java.sql.Date.valueOf(datadenasc);

				System.out.print("\nInforme o logradouro: ");
				String logradouro = teclado.next();
				
				String ATUALIZAR = "update veterinario set cpf=?, nome=?, "
						+ "datadenasc=?, logradouro=? where registro= "+ registro + " ";
				PreparedStatement upd = conn.prepareStatement(ATUALIZAR);
				
				upd.setString(1, cpf);
				upd.setString(2, nome);
				upd.setDate(3, dtValue);
				upd.setString(4, logradouro);
				
				upd.executeUpdate();
				upd.close();
				desconectar(conn);
				
				System.out.println("O veterinario " + nome + " foi atualizado com sucesso.");
			}else {
				System.out.println("Nao existe veterinario com o registro informado.");
			}


		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("Nao foi possivel atualizar o veterinario.");
			System.exit(-42);
		}		
	}

	public static void atualizartutor() {
		System.out.print("Informe o CPF do tutor: ");
		String cpf = teclado.next();
		
		String BUSCAR_POR_PK = "select * from tutor where cpf=?";
		
		try {
			Connection conn = conectar();
			PreparedStatement tutor = conn.prepareStatement(
					BUSCAR_POR_PK,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			tutor.setString(1, cpf);
			ResultSet res = tutor.executeQuery();

			res.last();
			int qtd = res.getRow();
			res.beforeFirst();
			
			if(qtd > 0) {
				System.out.print("\nInforme o nome: ");
				String nome = teclado.next();

				System.out.print("\nInforme a data de nascimento: ");
				String datadenasc = teclado.next();
				java.sql.Date dtValue = java.sql.Date.valueOf(datadenasc);

				System.out.print("\nInforme o logradouro: ");
				String logradouro = teclado.next();
				
				String ATUALIZAR = "update tutor set nome=?, "
						+ "datadenasc=?, logradouro=? where cpf= "+ cpf + " ";
				PreparedStatement upd = conn.prepareStatement(ATUALIZAR);
				
				upd.setString(1, nome);
				upd.setDate(2, dtValue);
				upd.setString(3, logradouro);
				
				upd.executeUpdate();
				upd.close();
				desconectar(conn);
				
				System.out.println("O tutor " + nome + " foi atualizado com sucesso.");
			}else {
				System.out.println("Nao existe tutor com o CPF informado.");
			}


		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("Nao foi possivel atualizar o tutor.");
			System.exit(-42);
		}		
	}

	public static void atualizaranimal() {
		System.out.print("Informe o ID do animal: ");
		int codigo = teclado.nextInt();
		
		String BUSCAR_POR_PK = "select * from animal where codigo=?";
		
		try {
			Connection conn = conectar();
			PreparedStatement animal = conn.prepareStatement(
					BUSCAR_POR_PK,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			animal.setInt(1, codigo);
			ResultSet res = animal.executeQuery();

			res.last();
			int qtd = res.getRow();
			res.beforeFirst();
			
			if(qtd > 0) {
				System.out.print("\nInforme o nome do animal: ");
				String nomeanimal = teclado.next();
				
				System.out.print("\nInforme a raca: ");
				String raca = teclado.next();

				System.out.print("\nInforme a data de nascimento: ");
				String datadenasc = teclado.next();
				java.sql.Date dtValue = java.sql.Date.valueOf(datadenasc);

				String ATUALIZAR = "update animal set nomeanimal=?, "
						+ "raca=?, datadenasc=? where codigo= "+ codigo + " ";
				PreparedStatement upd = conn.prepareStatement(ATUALIZAR);
				
				upd.setString(1, nomeanimal);
				upd.setString(2, raca);
				upd.setDate(3, dtValue);
			
				upd.executeUpdate();
				upd.close();
				desconectar(conn);
				
				System.out.println("O animal " + nomeanimal + " foi atualizado com sucesso.");
			}else {
				System.out.println("Nao existe animal com o codigo informado.");
			}


		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("Nao foi possivel atualizar o animal.");
			System.exit(-42);
		}	
	}

	public static void atualizarconsulta() {
		System.out.print("Informe o ID do agendamento: ");
		int codigoagendamento = teclado.nextInt();
		
		String BUSCAR_POR_PK = "select * from agendamento where codigoagendamento=?";
		
		try {
			Connection conn = conectar();
			PreparedStatement agendamento = conn.prepareStatement(
					BUSCAR_POR_PK,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			agendamento.setInt(1, codigoagendamento);
			ResultSet res = agendamento.executeQuery();

			res.last();
			int qtd = res.getRow();
			res.beforeFirst();
			
			if(qtd > 0) {
				System.out.print("\nInforme a data do agendamento: ");
				String dataagendamento = teclado.next();
				java.sql.Date dtValue = java.sql.Date.valueOf(dataagendamento);
				
				
				System.out.print("\nInforme a hora do agendamento: ");
				String horaagendamento = teclado.next();
				java.sql.Time dtValue1 = java.sql.Time.valueOf(horaagendamento);
				
				System.out.print("\nInforme o ID do veterinario: ");
				int idvet = teclado.nextInt();

				System.out.print("\nInforme o ID do animal: ");
				int idanimal = teclado.nextInt();
				
				String ATUALIZAR = "update agendamento set dataagendamento=?, "
						+ "horaagendamento=?, veterinario=?, animal=? where codigoagendamento= "+ codigoagendamento + " ";
				PreparedStatement upd = conn.prepareStatement(ATUALIZAR);
				
				upd.setDate(1, dtValue);
				upd.setTime(2, dtValue1);
				upd.setInt(3, idvet);
				upd.setInt(4, idanimal);
			
				upd.executeUpdate();
				upd.close();
				desconectar(conn);
				
				System.out.println("O agendamento foi atualizado para o dia " + dtValue + " as " + dtValue1 + " ");
			}else {
				System.out.println("Nao existe agendamento com o codigo informado.");
			}


		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("Nao foi possivel atualizar o agendamento.");
			System.exit(-42);
		}	
	}

	public static void deletarveterinario() {
		String DELETAR = "delete from veterinario where registro=?";
		String BUSCAR_POR_PK = "select * from veterinario where registro=?";
		
		System.out.print("Informe o registro do veterinario: ");
		int registro = teclado.nextInt();
		
		try {
			Connection conn = conectar();
			PreparedStatement busca = conn.prepareStatement(BUSCAR_POR_PK,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			busca.setInt(1, registro);
			ResultSet res = busca.executeQuery();

			res.last();
			int qtd = res.getRow();
			res.beforeFirst();
			
			if(qtd > 0) {
				PreparedStatement del = conn.prepareStatement(DELETAR);
				del.setInt(1, registro);
				del.executeUpdate();
				del.close();
				desconectar(conn);
				
				System.out.println("O veterinario foi deletado com sucesso.");
			}else {
				System.out.println("Nao existe veterinario com o registro informado.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro ao deletar veterinario.");
			System.exit(-42);
		}
	}

	public static void deletartutor() {
		String DELETAR = "delete from tutor where cpf=?";
		String BUSCAR_POR_PK = "select * from tutor where cpf=?";
		
		System.out.print("Informe o CPF do tutor: ");
		String cpf = teclado.next();
		
		try {
			Connection conn = conectar();
			PreparedStatement busca = conn.prepareStatement(BUSCAR_POR_PK,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			busca.setString(1, cpf);
			ResultSet res = busca.executeQuery();

			res.last();
			int qtd = res.getRow();
			res.beforeFirst();
			
			if(qtd > 0) {
				PreparedStatement del = conn.prepareStatement(DELETAR);
				del.setString(1, cpf);
				del.executeUpdate();
				del.close();
				desconectar(conn);
				
				System.out.println("O tutor foi deletado com sucesso.");
			}else {
				System.out.println("Nao existe tutor com o CPF informado.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro ao deletar tutor.");
			System.exit(-42);
		}
	}

	public static void deletaranimal() {
		String DELETAR = "delete from animal where codigo=?";
		String BUSCAR_POR_PK = "select * from animal where codigo=?";
		
		System.out.print("Informe o codigo do animal: ");
		int codigo = teclado.nextInt();
		
		try {
			Connection conn = conectar();
			PreparedStatement busca = conn.prepareStatement(BUSCAR_POR_PK,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			busca.setInt(1, codigo);
			ResultSet res = busca.executeQuery();

			res.last();
			int qtd = res.getRow();
			res.beforeFirst();
			
			if(qtd > 0) {
				PreparedStatement del = conn.prepareStatement(DELETAR);
				del.setInt(1, codigo);
				del.executeUpdate();
				del.close();
				desconectar(conn);
				
				System.out.println("O animal foi deletado com sucesso.");
			}else {
				System.out.println("Nao existe animal com o codigo informado.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro ao deletar animal.");
			System.exit(-42);
		}
	}

	public static void deletarconsulta() {
		String DELETAR = "delete from agendamento where codigoagendamento=?";
		String BUSCAR_POR_PK = "select * from agendamento where codigoagendamento=?";
		
		System.out.print("Informe o codigo do agendamento: ");
		int codigoagendamento = teclado.nextInt();
		
		try {
			Connection conn = conectar();
			PreparedStatement busca = conn.prepareStatement(BUSCAR_POR_PK,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			busca.setInt(1, codigoagendamento);
			ResultSet res = busca.executeQuery();

			res.last();
			int qtd = res.getRow();
			res.beforeFirst();
			
			if(qtd > 0) {
				PreparedStatement del = conn.prepareStatement(DELETAR);
				del.setInt(1, codigoagendamento);
				del.executeUpdate();
				del.close();
				desconectar(conn);
				
				System.out.println("O agendamento foi deletado com sucesso.");
			}else {
				System.out.println("Nao existe agendamento com o codigo informado.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro ao deletar agendamento.");
			System.exit(-42);
		}
	}

	public static void menu() {

		int opr;
		do {
			System.out.println("====== Agenda ======");
			System.out.println("Selecione uma opcao: ");
			System.out.println("1 - Listar.");
			System.out.println("2 - Inserir.");
			System.out.println("3 - Atualizar.");
			System.out.println("4 - Deletar.\n");

			int opcao = teclado.nextInt();

			if (opcao == 1) {
				System.out.println("Selecione uma opcao: ");
				System.out.println("1 - Listar veterinario.");
				System.out.println("2 - Listar tutor.");
				System.out.println("3 - Listar animal.");
				System.out.println("4 - Listar agendamento.\n");
				int op = teclado.nextInt();

				if (op == 1) {
					listarveterinario();
				} else if (op == 2) {
					listartutor();
				} else if (op == 3) {
					listaranimal();
				} else if (op == 4) {
					listaragenda();
				} else {
					System.out.println("Opcao invalida!");
				}
			} else if (opcao == 2) {
				System.out.println("Selecione uma opcao: ");
				System.out.println("1 - Inserir veterinario.");
				System.out.println("2 - Inserir tutor.");
				System.out.println("3 - Inserir animal.");
				System.out.println("4 - Inserir agendamento.\n");

				int op = teclado.nextInt();

				if (op == 1) {
					inserirveterinario();
				} else if (op == 2) {
					inserirtutor();
				} else if (op == 3) {
					inseriranimal();
				} else if (op == 4) {
					inserirconsulta();
				} else {
					System.out.println("Opcao invalida!");
				}
			} else if (opcao == 3) {
				System.out.println("Selecione uma opcao: ");
				System.out.println("1 - Atualizar veterinario.");
				System.out.println("2 - Atualizar tutor.");
				System.out.println("3 - Atualizar animal.");
				System.out.println("4 - Atualizar agendamento.\n");

				int op = teclado.nextInt();

				if (op == 1) {
					atualizarveterinario();
				} else if (op == 2) {
					atualizartutor();
				} else if (op == 3) {
					atualizaranimal();
				} else if (op == 4) {
					atualizarconsulta();
				} else {
					System.out.println("Opcao invalida!");
				}
			} else if (opcao == 4) {
				System.out.println("Selecione uma opcao: ");
				System.out.println("1 - Deletar veterinario.");
				System.out.println("2 - Deletar tutor.");
				System.out.println("3 - Deletar animal.");
				System.out.println("4 - Deletar agendamento.\n");

				int op = teclado.nextInt();

				if (op == 1) {
					deletarveterinario();
				} else if (op == 2) {
					deletartutor();
				} else if (op == 3) {
					deletaranimal();
				} else if (op == 4) {
					deletarconsulta();
				} else {
					System.out.println("Opcao invalida!");
				}
			} else {
				System.out.println("Opcao invalida!");
			}

			System.out.println("\nDeseja continuar?\n1 - Sim\n2 - Nao");
			opr = teclado.nextInt();
			while (opr != 1 && opr != 2) {
				System.out.println("Opcao invalida!");
				System.out.println("\nDeseja continuar?\n1 - Sim\n2 - Nao");
				opr = teclado.nextInt();
			}

		} while (opr != 2);

		teclado.close();
	}

}
