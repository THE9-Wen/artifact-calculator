package com.wenhao.calculator.character;

import com.wenhao.calculator.artifact.model.Artifact;
import com.wenhao.calculator.artifact.model.ArtifactSub;
import com.wenhao.calculator.calculator.Damage;
import com.wenhao.calculator.calculator.Reaction;
import com.wenhao.calculator.common.exceptions.ArtifactException;
import com.wenhao.calculator.monster.Monster;
import com.wenhao.calculator.weapon.Weapon;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Character
 *
 * @author: Wenhao Tong
 * @date: 2023/6/19
 */
@Data
@ToString(exclude = "basicValue")
public abstract class BaseCharacter implements Cloneable {
    protected BasicValue basicValue;

    protected Integer level;

    protected Double atk;

    protected Double hp;

    protected Double critDmg;

    protected Double critRate;

    protected Double defence;

    protected Double charging;

    protected Double bonus;

    protected Double mastery;

    protected BaseCharacter(BasicValue basicValue) {
        this.basicValue = basicValue;
        this.level = 89;
    }

    public void equipWeapon(Weapon weapon) {
        if (this.basicValue.getWeapon() != weapon.getType()) {
            throw new ArtifactException("错误的武器类型！");
        }
        basicValue.setAtk(basicValue.getAtk() + weapon.getAtk());
        atk = basicValue.getAtk();
        ArtifactSub main = weapon.getMain();
        updateCharacterValue(main);
        List<ArtifactSub> extras = weapon.getExtra();
        extras.forEach(this::updateCharacterValue);
    }

    public void updateCharacterValue(ArtifactSub sub) {
        switch (sub.getKeyword()) {
            case ATK -> atk += sub.getValue() * basicValue.getAtk();
            case ATK_ABS -> atk += sub.getValue();
            case HP -> hp += sub.getValue() * basicValue.getHp();
            case HP_ABS -> hp += sub.getValue();
            case DEFENCE -> defence += sub.getValue() * basicValue.getDefence();
            case DEFENCE_ABS -> defence += sub.getValue();
            case MASTERY -> mastery += sub.getValue();
            case CRIT_DMG -> critDmg += sub.getValue();
            case CRIT_RATE -> critRate += sub.getValue();
            case ENERGY_RECHARGE -> charging += sub.getValue();
        }
        if (sub.getKeyword().isBonus()) {
            bonus += sub.getValue();
        }
    }

    public Double levelResist(Monster monster) {
        return (level + 100.0) / (level + monster.getLevel() + 200.0);
    }

    public Double elementResist(Monster monster) {
        Double resist = monster.getResist();
        if (resist < 0.0) {
            return 1.0 - resist / 2.0;
        } else if (resist < 0.75) {
            return 1.0 - resist;
        } else {
            return 1.0 / (1.0 + 4.0 * resist);
        }
    }

    public Double basicDamage() {
        return atk * basicValue.getSkillDmg();
    }

    public void equipArtifact(Artifact artifact) {
        ArtifactSub main = artifact.getMain();
        updateCharacterValue(main);
        List<ArtifactSub> subs = artifact.getSubs();
        subs.forEach(this::updateCharacterValue);
    }

    /**
     * 角色天赋的加成
     */
    public abstract void talent();

    /**
     * 设置角色反应类型
     *
     * @param damage 伤害对象
     */
    public abstract void setReaction(Damage damage);

    public Damage hit(Monster monster) {
        Damage damage = new Damage();
        talent();
        party();
        damage.setBasicDmg(basicDamage() * levelResist(monster) * elementResist(monster) * (bonus + 1.0));
        damage.setCritDmg(critDmg);
        damage.setReaction(Reaction.AGGRAVATE);
        damage.setCritRate(critRate);
        damage.setMastery(mastery);
        setReaction(damage);
        return damage;
    }

    public abstract void party();

    public Boolean compareDamage(Damage damage1, Damage damage2) {
        return damage1.expectationDamage(true) < damage2.expectationDamage(true);
    }

    @Override
    public BaseCharacter clone() {
        // basicValue中的值不会去改变 其他值都是final类型 所以只需要浅拷贝即可
        try {
            BaseCharacter clone = (BaseCharacter) super.clone();
            clone.basicValue = clone.basicValue.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
