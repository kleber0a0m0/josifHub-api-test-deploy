package br.edu.josifHubapi.domain;

import br.edu.josifHubapi.enums.SituacaoUsuario;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "codigo")
@Transactional
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    private String email;
    private String senha;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true, unique = false)
    private SituacaoUsuario situacao;

    private String tokenConfirmacaoCadastro;

    public Usuario(String email, String senha, String tokenConfirmacaoCadastro) {
        this.email = email;
        this.senha = senha;
        this.tokenConfirmacaoCadastro = tokenConfirmacaoCadastro;
        this.situacao = SituacaoUsuario.PENDENTE_VALIDACAO_EMAIL;

    }

    public Usuario(User user) {
        this.email = user.getUsername();
        this.senha = user.getPassword();
        this.roles = user.getAuthorities().stream()
                .map(authority -> {
                    if (authority instanceof Roles) {
                        return (Roles) authority;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "userCodigo"),
            inverseJoinColumns = @JoinColumn(name = "roleCodigo"))
    private List<Roles> roles;

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("roles: "+this.roles);
        return this.roles;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}